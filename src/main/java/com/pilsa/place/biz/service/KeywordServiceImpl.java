package com.pilsa.place.biz.service;

import com.pilsa.place.biz.client.service.ClientService;
import com.pilsa.place.biz.client.vo.request.KakaoRequest;
import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.MergeResponse;
import com.pilsa.place.biz.client.vo.response.MergeSimpleResponse;
import com.pilsa.place.biz.client.vo.response.NaverResponse;
import com.pilsa.place.biz.service.dto.PlaceTransactionDTO;
import com.pilsa.place.biz.service.mapper.PlaceSearchMapper;
import com.pilsa.place.biz.vo.request.PlaceRequest;
import com.pilsa.place.biz.vo.response.KeywordResponse;
import com.pilsa.place.biz.vo.response.PlaceResponse;
import com.pilsa.place.common.code.ResponseCode;
import com.pilsa.place.common.code.VersionInfoCode;
import com.pilsa.place.common.constant.ApiConstant;
import com.pilsa.place.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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

    public KeywordServiceImpl(ClientService clientService, PlaceSearchMapper placeSearchMapper) {
        this.placeSearchMapper = placeSearchMapper;
    }

    @Override
    public void saveSearchHistory(PlaceRequest request) {
        log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Async Start "+MDC.get(ApiConstant.TRANSACTION_ID));

        placeSearchMapper.insertSearchHistory(PlaceTransactionDTO.builder()
                .query("REQUIRES_NEW")
                .build());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Async End "+MDC.get(ApiConstant.TRANSACTION_ID));
        throw new ServiceException(ResponseCode.TIMEOUT);
    }

}
