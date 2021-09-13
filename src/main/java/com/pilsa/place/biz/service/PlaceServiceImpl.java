package com.pilsa.place.biz.service;

import com.pilsa.place.biz.client.service.ClientService;
import com.pilsa.place.biz.client.vo.request.KakaoRequest;
import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.MergeResponse;
import com.pilsa.place.biz.client.vo.response.MergeSimpleResponse;
import com.pilsa.place.biz.client.vo.response.NaverResponse;
import com.pilsa.place.biz.service.mapper.PlaceSearchMapper;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import com.pilsa.place.common.code.VersionInfoCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Invest product service.
 *
 * @author pilsa_home1
 */
@Validated
@Slf4j
@Service
public class PlaceServiceImpl implements PlaceService {

    @Value("${spring.profiles.active}")
    private String profileActive;

    private final ClientService clientService;
    private final PlaceSearchMapper placeSearchMapper;

    public PlaceServiceImpl(ClientService clientService, PlaceSearchMapper placeSearchMapper) {
        this.clientService = clientService;
        this.placeSearchMapper = placeSearchMapper;
    }

    @Override
    public Mono<KakaoResponse> searchPlaceMono(PlaceRequest request) {
        return clientService.callKaKaoSearch(KakaoRequest.builder()
                .baseUrl("https://dapi.kakao.com")
                .uri("local/search/keyword.json")
                .version(VersionInfoCode.V2)
                .query(request.getQuery())
                .size(5)
                .build());
    }

    @Override
    public PlaceResponse searchPlaceMerge(PlaceRequest request) {
        /*======================================================================================
         * i) api 조회
        ======================================================================================*/
        MergeResponse apiResponse = clientService.callParallelSearch(request);

        List<PlaceResponse.Place> mergePlaces = new ArrayList<>();
        Iterator<KakaoResponse.Document> kakaoIterator = apiResponse.getKakaoResponse().getDocuments().iterator();
        Iterator<NaverResponse.Item> naverIterator = apiResponse.getNaverResponse().getItems().iterator();

        /*======================================================================================
         * i) 교집합
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
         * i) KakaoResponse 에 남아있는것이 있다면
        ======================================================================================*/
        apiResponse.getKakaoResponse().getDocuments().forEach(document ->
                mergePlaces.add(PlaceResponse.Place.builder()
                        .placeName(document.getPlaceName())
                        .addressName(document.getAddressName())
                        .roadAddressName(document.getRoadAddressName())
                        .placeUrl(document.getPlaceUrl())
                        .build()));

        /*======================================================================================
         * i) NaverResponse 에 남아있는것이 있다면
        ======================================================================================*/
        apiResponse.getNaverResponse().getItems().forEach(item ->
                mergePlaces.add(PlaceResponse.Place.builder()
                        .placeName(item.getTitle())
                        .addressName(item.getAddress())
                        .roadAddressName(item.getRoadAddress())
                        .placeUrl(item.getLink())
                        .build()));

        return PlaceResponse.builder()
                .meta(PlaceResponse.Meta.builder().totalCount(mergePlaces.size()).build())
                .places(mergePlaces)
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
    @Cacheable(cacheNames = "popularKeywordCache", key="'fewHour'")
    public KeywordResponse getPopularKeywordsFromCache() {
        List<KeywordResponse.Keyword> keywordList = placeSearchMapper.selectPopularKeywords().stream()
                .map(transactionDTO -> KeywordResponse.Keyword.builder()
                        .query(transactionDTO.getQuery())
                        .queryCnt(transactionDTO.getQueryCnt())
                        .build())
                .collect(Collectors.toList());

        return KeywordResponse.builder()
                .meta(KeywordResponse.Meta.builder().totalCount(keywordList.size()).build())
                .keywords(keywordList)
                .build();
    }


}
