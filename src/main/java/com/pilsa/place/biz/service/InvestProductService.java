package com.pilsa.place.biz.service;

import com.pilsa.place.biz.vo.request.InvestListRequest;
import com.pilsa.place.biz.vo.request.InvestRequest;
import com.pilsa.place.biz.vo.request.InvestTranRequest;
import com.pilsa.place.biz.vo.response.InvestListResponse;
import com.pilsa.place.biz.vo.response.InvestResponse;
import com.pilsa.place.biz.vo.response.InvestTranResponse;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

/**
 * The interface Invest product service.
 * 상품을 조회하거나 투자 서비스를 제공합니다.
 */
public interface InvestProductService {

    /**
     * Gets place products.
     * 투자상품 목록을 조회합니다.
     *
     * @param request the request
     * @return the place products
     */
    @Transactional
    InvestListResponse getInvestProducts(InvestListRequest request);

    /**
     * Invest in products place response.
     * 상품에 대한 유효성 검증과 투자를 수행합니다.
     *
     * @param request the request
     * @return the place response
     */
    @Transactional
    InvestResponse investInProducts(@Valid InvestRequest request);

    /**
     * Gets my place transactions.
     * 회원이 투자한 상품에대한 내역을 조회합니다.
     *
     * @param request the request
     * @return the my place transactions
     */
    @Transactional
    InvestTranResponse getMyInvestTransactions(InvestTranRequest request);
}
