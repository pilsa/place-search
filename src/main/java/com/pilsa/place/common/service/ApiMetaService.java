package com.pilsa.place.common.service;

import com.pilsa.place.common.code.ApiMetaCode;
import com.pilsa.place.common.service.dto.ApiMetaDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * The interface Api meta service.
 *
 * @author pilsa_home1
 * @since 2021 -09-13 오전 12:31
 */
public interface ApiMetaService {

    /**
     * 기관정보와 API META 정보를 조회한다.
     * Gets api meta.
     *
     * @return the api meta
     */
    @Transactional
    List<ApiMetaCode> getApiMetaFromCache();
}
