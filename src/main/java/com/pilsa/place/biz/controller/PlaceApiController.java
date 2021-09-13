package com.pilsa.place.biz.controller;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.service.InvestProductService;
import com.pilsa.place.biz.service.PlaceService;
import com.pilsa.place.biz.service.ProductValidityService;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * 장소 검색 서비스 API Controller.
 */
@RestController
@RequestMapping("/v1/place")
@Tags(@Tag(name = "place", description = "장소 검색 서비스 API"))
public class PlaceApiController {

    private final InvestProductService investProductService;
    private final ProductValidityService productValidityService;

    private final PlaceService placeService;

    @Autowired
    public PlaceApiController(
            InvestProductService investProductService
            , ProductValidityService productValidityService
            , PlaceService placeService) {
        this.placeService = placeService;
        this.investProductService = investProductService;
        this.productValidityService = productValidityService;
    }

    @GetMapping("")
    @ApiOperation(
            value = "장소 검색 API", tags = "place",
            notes = "<b>카카오와 네이버의 검색 API에서 최대 10개를 추출합니다.</b>" +
                    "<li>카카오 결과 네이버 결과 모두 존재 : 최상위에 정렬</li>" +
                    "<li>둘 중 하나에만 존재하는 경우, 카카오 결과를 우선 정렬 후 네이버 결과 정렬</li>")
    public PlaceResponse getPlaces(PlaceRequest request){
        //PlaceResponse res = placeService.searchPlace(request);
        //PlaceResponse res = placeService.searchPlaceMerge(request);
        PlaceResponse res = placeService.searchPlaceMerge(request);
        return res;
    }


    @GetMapping("/keywords")
    @ApiOperation(
            value = "인기 키워드 API", tags = "place",
            notes = "카카오와 네이버의 리소스를 활용 하여 어쩌구")
    public KeywordResponse getPopularKeywords(){
        KeywordResponse res = placeService.getPopularKeywordsFromCache();
        return res;
    }

    // TODO : 테스트용도
    @GetMapping("/kakao-mono")
    @ApiOperation(
            value = "장소 검색 API", tags = "place",
            notes = "카카오와 네이버의 리소스를 활용 하여 어쩌구")
    public Mono<KakaoResponse> localSearchKeywordMono(PlaceRequest request){
        Mono<KakaoResponse> res = placeService.searchPlaceMono(request);
        return res;
    }

}
