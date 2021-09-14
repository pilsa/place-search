package com.pilsa.place.biz.client.service;

import com.pilsa.place.biz.client.vo.request.KakaoRequest;
import com.pilsa.place.biz.client.vo.request.NaverRequest;
import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.MergeResponse;
import com.pilsa.place.biz.client.vo.response.MergeSimpleResponse;
import com.pilsa.place.biz.client.vo.response.NaverResponse;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.common.code.VersionInfoCode;
import com.pilsa.place.framework.webclient.CommonClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.stream.Collectors;

/**
 *
 * @author pilsa_home1
 * @since 2021-09-11 오후 3:06
 */
@Slf4j
@Service
public class ClientService {

    @Autowired
    private Environment environment;

    @Value("${spring.profiles.active}")
    private String profileActive;

    private CommonClient httpClient;

    @Autowired
    @Qualifier("httpClient")
    public void setAccountListClientService(CommonClient httpClient) {
        this.httpClient = httpClient;
    }

    public Mono<KakaoResponse> callKaKaoSearch(PlaceRequest request){
        return httpClient.requestDataByGetMono(
                KakaoRequest.builder()
                        .baseUrl(environment.getProperty("endpoints.kakao.base-url"))
                        .uri("local/search/keyword.json")
                        .version(VersionInfoCode.V2)
                        .query(request.getQuery())
                        .size(5)
                        .build()
                , KakaoResponse.class);
    }

    public Mono<NaverResponse> callNaverSearch(PlaceRequest request){
         return httpClient.requestDataByGetMono(
                 NaverRequest.builder()
                         .baseUrl(environment.getProperty("endpoints.naver.base-url"))
                         .uri("search/local.json")
                         .version(VersionInfoCode.V1)
                         .query(request.getQuery())
                         .display(5)
                         .build()
                 , NaverResponse.class);
    }


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
}
