package com.pilsa.place.biz.controller;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.KakaoResponseList;
import com.pilsa.place.biz.service.PlaceService;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author pilsa_home1
 * @since 2021-10-21 오전 12:30
 */
@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/v1/reactive")
@Tags(@Tag(name = "reactive", description = "리액티브 API"))
public class ReactiveApiController {

    private final PlaceService placeService;

    @GetMapping("")
    public KakaoResponseList getFluxPlaces(PlaceRequest request){
        ParallelFlux<KakaoResponse> a = placeService.searchPlaceFlux();

        List<KakaoResponse> kakaoResponseList = new ArrayList<>();
        //a.doOnComplete(() -> kakaoResponseList.add(Mono.from(a).block()));
        log.debug(String.valueOf(kakaoResponseList.size()));

        a.subscribe();
        kakaoResponseList.addAll(Flux.from(a).collectList().block());



        return KakaoResponseList.builder().KakaoResponseList(kakaoResponseList).build();
    }

}
