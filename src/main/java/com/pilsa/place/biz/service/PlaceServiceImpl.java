package com.pilsa.place.biz.service;

import com.pilsa.place.biz.client.service.ClientService;
import com.pilsa.place.biz.client.vo.response.*;
import com.pilsa.place.biz.service.dto.PlaceCondition;
import com.pilsa.place.biz.service.mapper.PlaceSearchMapper;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import com.pilsa.place.common.constant.ApiConstant;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * @author pilsa_home1
 * @since 2021-09-14 오전 2:29
 */
@Validated
@Slf4j
@Service
public class PlaceServiceImpl implements PlaceService {

    private final ClientService clientService;
    private final keywordService keywordService;
    private final PlaceSearchMapper placeSearchMapper;

    public PlaceServiceImpl(ClientService clientService
            , keywordService keywordService
            , PlaceSearchMapper placeSearchMapper) {
        this.clientService = clientService;
        this.keywordService = keywordService;
        this.placeSearchMapper = placeSearchMapper;
    }

    @Override
    public Mono<KakaoResponse> searchPlaceMono(PlaceRequest request) {
        return clientService.callKaKaoSearch(request);
    }

    @Override
    public PlaceResponse searchPlaceMerge(PlaceRequest request) {
        /*======================================================================================
         * 1) 카카오와 네이버의 다른 End-Point를 병렬 호출하는 Clinet 서비스를 호출한다.
        ======================================================================================*/
        MergeResponse apiResponse = clientService.callParallelSearch(request);

        List<PlaceResponse.Place> mergePlaces = new ArrayList<>();
        Iterator<KakaoResponse.Document> kakaoIterator = apiResponse.getKakaoResponse().getDocuments().iterator();
        Iterator<NaverResponse.Item> naverIterator = apiResponse.getNaverResponse().getItems().iterator();

        /*======================================================================================
         * 2) 카카오와 네이버의 응답에서 교집합을 구한다. 기준은 장소명이다.
        ======================================================================================*/
        while (kakaoIterator.hasNext()) {
            KakaoResponse.Document document = kakaoIterator.next();

            while (naverIterator.hasNext()) {
                NaverResponse.Item item = naverIterator.next();

                if (document.getPlaceName().equals(item.getTitle())) {
                    mergePlaces.add(PlaceResponse.Place.builder()
                            .placeName(document.getPlaceName())
                            .addressName(document.getAddressName())
                            .roadAddressName(document.getRoadAddressName())
                            .placeUrl(document.getPlaceUrl())
                            .build());
                    kakaoIterator.remove();
                    naverIterator.remove();
                    break;
                }
            }
            if (kakaoIterator.hasNext()) {
                naverIterator = apiResponse.getNaverResponse().getItems().iterator();
            }
        }

        /*======================================================================================
         * 3) KakaoResponse 에 남아있는것이 있다면 추가한다.
        ======================================================================================*/
        apiResponse.getKakaoResponse().getDocuments().forEach(document ->
                mergePlaces.add(PlaceResponse.Place.builder()
                        .placeName(document.getPlaceName())
                        .addressName(document.getAddressName())
                        .roadAddressName(document.getRoadAddressName())
                        .placeUrl(document.getPlaceUrl())
                        .build()));

        /*======================================================================================
         * 4) NaverResponse 에 남아있는것이 있다면 추가한다.
        ======================================================================================*/
        apiResponse.getNaverResponse().getItems().forEach(item ->
                mergePlaces.add(PlaceResponse.Place.builder()
                        .placeName(item.getTitle())
                        .addressName(item.getAddress())
                        .roadAddressName(item.getRoadAddress())
                        .placeUrl(item.getLink())
                        .build()));


        /*======================================================================================
         * 5) 장소검색 이력을 등록한다.
         * Propagation REQUIRES_NEW 선언된 서비스를 비동기로 호출한다.
         * 통합된 검색 결과에 목록이 없으면 유효하지 않은 검색어로 판단하여 호출하지 않는다.
        ======================================================================================*/
        if (log.isDebugEnabled()){
            log.debug("Async & RequiresNew Test TRANSACTION_ID : {}",MDC.get(ApiConstant.TRANSACTION_ID));
        }
        if (mergePlaces.size() > 0 ) keywordService.saveSearchHistoryAsync(request);

        /*======================================================================================
         * 6) 통합된 검색 결과를 반환한다.
        ======================================================================================*/
        return PlaceResponse.builder()
                .meta(PlaceResponse.Meta.builder().totalCount(mergePlaces.size()).build())
                .places(mergePlaces)
                .build();
    }

    @Override
    @Cacheable(cacheNames = "popularKeywordCache", key="'fewHour'")
    public KeywordResponse getPopularKeywordsFromCache() {
        /*======================================================================================
         * 1) 인기 키워드 목록 조회한다.
        ======================================================================================*/
        List<KeywordResponse.Keyword> keywordList =
                placeSearchMapper.selectPopularKeywords(PlaceCondition.builder().fewHour(-24).build()).stream()
                        .map(transactionDTO -> KeywordResponse.Keyword.builder()
                                .query(transactionDTO.getQuery())
                                .rank(transactionDTO.getRank())
                                .queryCnt(transactionDTO.getQueryCnt())
                                .build())
                        .collect(Collectors.toList());

        return KeywordResponse.builder()
                .meta(KeywordResponse.Meta.builder().totalCount(keywordList.size()).build())
                .keywords(keywordList)
                .build();
    }

    @Override
    public PlaceResponse searchPlaceMergeSimpleData(PlaceRequest request) {
        /*======================================================================================
         * i) api 조회
        ======================================================================================*/
        MergeSimpleResponse apiResponse = clientService.callParallelSearchSimpleData(request);

        List<PlaceResponse.Place> mergePlaces = new ArrayList<>();
        /*======================================================================================
         * i) 교집합
        ======================================================================================*/
        mergePlaces = apiResponse.getKakaoResponse().stream()
                .filter(kakao -> apiResponse.getNaverResponse().stream()
                        .anyMatch(naver -> kakao.getPlaceName().equals(naver.getPlaceName())))
                .collect(Collectors.toList()).stream()
                .map(intersection -> PlaceResponse.Place.builder()
                        .placeName(intersection.getPlaceName())
                        .addressName(intersection.getAddressName())
                        .roadAddressName(intersection.getRoadAddressName())
                        .placeUrl(intersection.getPlaceUrl())
                        .build())
                .collect(Collectors.toList());


        return PlaceResponse.builder()
                .meta(PlaceResponse.Meta.builder()
                        .totalCount(mergePlaces.size())
                        .build())
                .places(mergePlaces)
                .build();
    }



    @Override
    public ParallelFlux<KakaoResponse> searchPlaceFlux() {
        return clientService.callSearchParallelFlux();
    }

}
