package com.pilsa.place.biz.controller;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.service.PlaceService;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;


/**
 * 장소 검색 서비스 API Controller.
 *
 * @author pilsa_home1
 * @since 2021-09-11 오전 9:33
 */
@RestController
@RequestMapping("/v1/place")
@Tags(@Tag(name = "place", description = "장소 검색 서비스 API"))
public class PlaceApiController {

    private final PlaceService placeService;

    public PlaceApiController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @GetMapping("")
    @ApiOperation(
            value = "장소 검색 API", tags = "place",
            notes = "<b>카카오와 네이버의 검색 API에서 최대 10개를 추출합니다.</b>" +
                    "<li>카카오 결과 네이버 결과 모두 존재하는 경우 : 최상위에 정렬</li>" +
                    "<li>둘 중 하나에만 존재하는 경우 : 카카오 결과를 우선 정렬 후 네이버 결과 정렬</li>")
    public PlaceResponse getPlaces(PlaceRequest request){
        return placeService.searchPlaceMerge(request);
    }


    @GetMapping("/keywords")
    @ApiOperation(
            value = "인기 키워드 API", tags = "place",
            notes = "<b>사용자들이 많이 검색한 순서대로 최대 10개의 검색 키워드를 제공합니다.</b>" +
                    "<li>24시간 범위에서 인기 키워드 순위를 추출합니다.</li>" +
                    "<li>대규모 트래픽에 대한 부하를 처리하기 위해 Cache (10초 단위 TTL) 처리 하였습니다. </li>")
    public KeywordResponse getPopularKeywords(){
        return placeService.getPopularKeywordsFromCache();
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
