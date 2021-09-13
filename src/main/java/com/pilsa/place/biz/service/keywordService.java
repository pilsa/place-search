package com.pilsa.place.biz.service;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


/**
 * The interface Place service.
 *
 * @author pilsa_home1
 * @since 2021 -09-14 오전 2:17
 */
public interface keywordService {

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    void saveSearchHistory(@Valid PlaceRequest request);
}
