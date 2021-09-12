package com.pilsa.place.biz.controller;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.MergeResponse;
import com.pilsa.place.biz.service.InvestProductService;
import com.pilsa.place.biz.service.PlaceService;
import com.pilsa.place.biz.service.ProductValidityService;
import com.pilsa.place.biz.vo.request.InvestListRequest;
import com.pilsa.place.biz.vo.request.InvestRequest;
import com.pilsa.place.biz.vo.request.InvestTranRequest;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.InvestListResponse;
import com.pilsa.place.biz.vo.response.InvestResponse;
import com.pilsa.place.biz.vo.response.InvestTranResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/mono")
    @ApiOperation(
            value = "장소 검색 API", tags = "place",
            notes = "카카오와 네이버의 리소스를 활용 하여 어쩌구")
    public Mono<KakaoResponse> localSearchKeywordMono(PlaceRequest request){
        // KakaoResponse res = placeService.searchPlace(request);
        Mono<KakaoResponse> res = placeService.searchPlaceMono(request);
        /*
        KakaoResponse res =
        kaKaoClientService.searchLocal(KakaoRequest.builder()
                .version(VersionInfoCode.V2)
                .query(request.getQuery())
                .build());*/
        return res;
    }

    @GetMapping("")
    @ApiOperation(
            value = "장소 검색 API", tags = "place",
            notes = "카카오와 네이버의 리소스를 활용 하여 어쩌구")
    public PlaceResponse localSearchKeyword(PlaceRequest request){
        // KakaoResponse res = placeService.searchPlace(request);
        //PlaceResponse res = placeService.searchPlaceMerge(request);
        PlaceResponse res = placeService.searchPlaceMergeSimpleData(request);
        /*
        KakaoResponse res =
        kaKaoClientService.searchLocal(KakaoRequest.builder()
                .version(VersionInfoCode.V2)
                .query(request.getQuery())
                .build());*/
        return res;
    }






    /**
     * Gets place product list.
     *
     * @param request the request
     * @return the place product list
     */
    @PostMapping("/products")
    @ApiOperation(
            value = "전체 투자 상품 조회 API", tags = "place",
            notes = "<b>※ sortOption 정렬순서 파라미터 설명</b>" +
                    "<li>INCOME : 높은 수익률 순서</li>" +
                    "<li>PERIOD : 짧은 기간 순서</li>" +
                    "<li>CLOSING : 마감 임박 순서</li>")
    public InvestListResponse getInvestProductList(@RequestBody InvestListRequest request) {
        return investProductService.getInvestProducts(request);
    }

    /**
     * Invest in products place response.
     *
     * @param request the request
     * @return the place response
     */
    @PostMapping("")
    @ApiOperation(
            value = "투자하기 API", tags = "place",
            notes = "사용자 식별값, 상품ID, 투자금액을 투입하여 투자를 수행 합니다.")
    public InvestResponse investInProducts(@RequestBody InvestRequest request) {
        return investProductService.investInProducts(request);
    }

    /**
     * Gets my place transactions.
     *
     * @param request the request
     * @return the my place transactions
     */
    @PostMapping("/transactions")
    @ApiOperation(
            value = "나의 투자상품 조회 API", tags = "place",
            notes = "사용자가 투자한 모든 상품을 반환합니다.")
    public InvestTranResponse getMyInvestTransactions(@RequestBody InvestTranRequest request) {
        return investProductService.getMyInvestTransactions(request);
    }


}
