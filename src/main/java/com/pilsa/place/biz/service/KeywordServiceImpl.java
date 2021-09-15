package com.pilsa.place.biz.service;

import com.pilsa.place.biz.service.dto.PlaceTransactionDTO;
import com.pilsa.place.biz.service.mapper.PlaceSearchMapper;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.common.code.ResponseCode;
import com.pilsa.place.common.constant.ApiConstant;
import com.pilsa.place.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * 
 * @author pilsa_home1
 * @since 2021-09-14 오전 2:29
 */
@Validated
@Slf4j
@Service
public class KeywordServiceImpl implements keywordService {

    private final PlaceSearchMapper placeSearchMapper;

    public KeywordServiceImpl(PlaceSearchMapper placeSearchMapper) {
        this.placeSearchMapper = placeSearchMapper;
    }

    @Override
    public void saveSearchHistoryAsync(PlaceRequest request) {

        if (log.isDebugEnabled()){
            log.debug("Async & RequiresNew Test TRANSACTION_ID : {}",MDC.get(ApiConstant.TRANSACTION_ID));
        }

        placeSearchMapper.insertSearchHistory(PlaceTransactionDTO.builder()
                .query(request.getQuery())
                .build());
        /** ======================================================================================
         * transaction 분리 및 비동기 테스트를 위함.
        ======================================================================================= */
        if (false) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            throw new ServiceException(ResponseCode.TIMEOUT);
        }
    }

}
