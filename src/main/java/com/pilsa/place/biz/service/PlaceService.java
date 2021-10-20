package com.pilsa.place.biz.service;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.KakaoResponseList;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.Disposable;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import javax.validation.Valid;
import java.util.List;


/**
 * The interface Place service.
 *
 * @author pilsa_home1
 * @since 2021 -09-14 오전 2:17
 */
public interface PlaceService {

    /**
     * 카카오와 네이버의 다른 End-Point를 병렬 호출하는 Clinet 서비스.
     * 요구사항에 부합하는 통합된 결과를 반환한다.
     * Search place merge place response.
     *
     * @param request the request
     * @return the place response
     */
    @Transactional
    PlaceResponse searchPlaceMerge(@Valid PlaceRequest request);

    /**
     * Search place mono mono.
     *
     * @param request the request
     * @return the mono
     */
    @Transactional
    Mono<KakaoResponse> searchPlaceMono(@Valid PlaceRequest request);

    /**
     * Search place merge simple data place response.
     *
     * @param request the request
     * @return the place response
     */
    @Transactional
    PlaceResponse searchPlaceMergeSimpleData(@Valid PlaceRequest request);

    /**
     * Gets popular keywords from cache.
     *
     * @return the popular keywords from cache
     */
    KeywordResponse getPopularKeywordsFromCache();


    ParallelFlux<KakaoResponse> searchPlaceFlux();


}
