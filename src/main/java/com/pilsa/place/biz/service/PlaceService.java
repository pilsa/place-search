package com.pilsa.place.biz.service;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


public interface PlaceService {

    /**
     * Invest in products place response.
     * 상품에 대한 유효성 검증과 투자를 수행합니다.
     *
     * @param request the request
     * @return the place response
     */
//    @Transactional
//    KakaoResponse searchPlace(@Valid PlaceRequest request);
    @Transactional
    Mono<KakaoResponse> searchPlaceMono(@Valid PlaceRequest request);
    @Transactional
    PlaceResponse searchPlaceMerge(@Valid PlaceRequest request);
    @Transactional
    PlaceResponse searchPlaceMergeSimpleData(@Valid PlaceRequest request);

    @Transactional
    KeywordResponse getPopularKeywordsFromCache();
}
