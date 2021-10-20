package com.pilsa.place.biz.client.service;

import com.pilsa.place.biz.client.vo.request.KakaoRequest;
import com.pilsa.place.biz.client.vo.request.NaverRequest;
import com.pilsa.place.biz.client.vo.response.*;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.common.code.ApiCode;
import com.pilsa.place.framework.webclient.CommonClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Schedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Client service.
 *
 * @author pilsa_home1
 * @since 2021 -09-11 오후 3:06
 */
@Slf4j
@Service
public class ClientService {

    private CommonClient httpClient;

    /**
     * Sets account list client service.
     *
     * @param httpClient the http client
     */
    @Autowired
    @Qualifier("httpClient")
    public void setAccountListClientService(CommonClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * kakaoMono 와 naverMono 를 병렬로 요청하여 응답을 통합하여 반환한다.
     * Call parallel search merge response.
     *
     * @param request the request
     * @return the merge response
     */
    public MergeResponse callParallelSearch(PlaceRequest request){

        Mono<KakaoResponse> kakaoMono = callKaKaoSearch(request).subscribeOn(Schedulers.elastic());
        Mono<NaverResponse> naverMono = callNaverSearch(request).subscribeOn(Schedulers.elastic());

        MergeResponse response = Mono.zip(kakaoMono, naverMono, (kakao, naver) -> {
            return MergeResponse.builder()
                    .kakaoResponse(kakao)
                    .naverResponse(naver)
                    .build();
        }).block();
        return response;
    }

    /**
     * 카카오 키워드로 장소 검색 API를 호출한다.
     * UserAgent 에게 입력받은 키워드, 내부적으로 관리하는 API-Code, 디폴트 사이즈, 응답 Bean
     * 구현체는 {@link com.pilsa.place.framework.webclient.HttpClient}
     *
     * @param request the request
     * @return the mono
     */
    public Mono<KakaoResponse> callKaKaoSearch(PlaceRequest request){
        return httpClient.requestDataByGetMono(
                KakaoRequest.builder()
                        .apiCode(ApiCode.Code.KAKAO_SE01)
                        .query(request.getQuery())
                        .size(5)
                        .build()
                , KakaoResponse.class);
    }

    /**
     * 네이버 지역 검색 API를 호출한다.
     * Call naver search mono.
     *
     * @param request the request
     * @return the mono
     */
    public Mono<NaverResponse> callNaverSearch(PlaceRequest request){
         return httpClient.requestDataByGetMono(
                 NaverRequest.builder()
                         .apiCode(ApiCode.Code.NAVER_SE01)
                         .query(request.getQuery())
                         .display(5)
                         .build()
                 , NaverResponse.class);
    }


    /**
     * kakaoMono 와 naverMono 를 병렬로 요청하여 응답을 경량 통합하여 반환한다.
     * Call parallel search simple data merge simple response.
     *
     * @param request the request
     * @return the merge simple response
     */
    public MergeSimpleResponse callParallelSearchSimpleData(PlaceRequest request){
        Mono<KakaoResponse> kakaoMono = callKaKaoSearch(request).subscribeOn(Schedulers.elastic());
        Mono<NaverResponse> naverMono = callNaverSearch(request).subscribeOn(Schedulers.elastic());

        MergeSimpleResponse response = Mono.zip(kakaoMono, naverMono, (kakao, naver) ->
                MergeSimpleResponse.builder()

                        .kakaoResponse(kakao.getDocuments().stream().map( document ->
                                MergeSimpleResponse.MergePlace.builder()
                                        .placeName(document.getPlaceName())
                                        .addressName(document.getAddressName())
                                        .roadAddressName(document.getRoadAddressName())
                                        .placeUrl(document.getPlaceUrl())
                                        .build())
                                .collect(Collectors.toList()))

                        .naverResponse(naver.getItems().stream().map( item ->
                                MergeSimpleResponse.MergePlace.builder()
                                        .placeName(item.getTitle())
                                        .addressName(item.getAddress())
                                        .roadAddressName(item.getRoadAddress())
                                        .placeUrl(item.getLink())
                                        .build())
                                .collect(Collectors.toList()))

                        .build())
                .block();
        return response;
    }


    public ParallelFlux<KakaoResponse> callSearchParallelFlux(){
    //public KakaoResponseList callSearchParallelFlux(){

        List<PlaceRequest> placeRequestList = new ArrayList<>(5);
        placeRequestList.add(PlaceRequest.builder().query("핀크").build());
        placeRequestList.add(PlaceRequest.builder().query("토스").build());
        placeRequestList.add(PlaceRequest.builder().query("카카오페이").build());
        placeRequestList.add(PlaceRequest.builder().query("카카오뱅크").build());
        placeRequestList.add(PlaceRequest.builder().query("토스뱅크").build());
        List<KakaoResponse> rtn = new ArrayList<>();

        //List<Flux<KakaoResponse>> listFlux =
        return
        Flux.fromIterable(placeRequestList)
                .parallel(5)
                .runOn(Schedulers.parallel())
                .flatMap(this::callKaKaoSearch)

                /*
                .subscribe( kakaoResponse -> {
                    kakaoResponse.getDocuments().forEach(document ->log.debug("kakaoResponse :: [{}] {}",document.getPlaceName(),document.getAddressName()));
                    rtn.add(Mono.just(kakaoResponse).block());
                }, throwable -> {
                    log.debug("kakaoResponse throwable :: {}", throwable);
                }, () -> {
                    log.debug("aaa :: {}",placeRequestList.size());
                })
                 */
        ;

        //.map(kakaoResponse -> Mono.just(kakaoResponse).block())
        //.ordered((kakaoResponse, t1) -> kakaoResponse.getDocuments().equals(t1.getDocuments()));
        //Mono<KakaoResponse> kakaoMono = callKaKaoSearch(request).subscribeOn(Schedulers.elastic());

    }
}
