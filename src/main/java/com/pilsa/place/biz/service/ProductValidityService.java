package com.pilsa.place.biz.service;

import com.pilsa.place.biz.service.dto.InvestCondition;
import com.pilsa.place.biz.vo.response.InvestResponse;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Product validity service.
 * 상품에 대한 유효성 검증을 위한 서비스를 제공합니다.
 *
 * @author pilsa_home1
 */
public interface ProductValidityService {

    /**
     * Product validity place response.
     *
     * @param request the request
     * @return the place response
     */
    @Transactional
    InvestResponse productValidity(InvestCondition request);


}
