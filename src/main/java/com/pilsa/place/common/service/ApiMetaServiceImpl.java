package com.pilsa.place.common.service;

import com.pilsa.place.common.code.ApiMetaCode;
import com.pilsa.place.common.service.dto.ApiMetaDTO;
import com.pilsa.place.common.service.mapper.ApiMetaMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

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
@AllArgsConstructor
public class ApiMetaServiceImpl implements ApiMetaService {

    private final ApiMetaMapper apiMetaMapper;

    @Override
    @Cacheable(cacheNames = "apiMetaCache", key="'apiMeta'")
    public List<ApiMetaCode> getApiMetaFromCache() {
        List<ApiMetaCode> a = apiMetaMapper.selectApiMeta().stream()
                .map( apiMetaDTO ->  ApiMetaCode.builder()
                        .key(apiMetaDTO.getApiCode())
                        .value(apiMetaDTO.getApiNm())
                        .alinCode(apiMetaDTO.getAlinCode())
                        .alinDomn(apiMetaDTO.getAlinDomn())
                        .alinAuthDvcd(apiMetaDTO.getAlinAuthDvcd())
                        .clientId(apiMetaDTO.getClientId())
                        .clientSecret(apiMetaDTO.getClientSecret())
                        .apiKey(apiMetaDTO.getApiKey())
                        .apiVer(apiMetaDTO.getApiVer())
                        .apiUri(apiMetaDTO.getApiUri())
                        .build())
                .collect(Collectors.toList());
        return a;
    }
}
