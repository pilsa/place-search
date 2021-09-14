package com.pilsa.place.common.code.service;

import com.pilsa.place.common.code.ApiCode;
import com.pilsa.place.common.code.ApiMetaCode;
import com.pilsa.place.common.service.ApiMetaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The type Alliance code service.
 */
@Slf4j
@Service
public class ApiCodeService {

    private final ApiMetaService apiMetaService;

    public ApiCodeService(ApiMetaService apiMetaService) {
        this.apiMetaService = apiMetaService;
    }

    public ApiMetaCode getApiMetaCodeByKey(ApiCode.Code key){
        ApiMetaCode aa =
        apiMetaService.getApiMetaFromCache().stream()
                .filter(allianceList -> allianceList.getKey().equals(key))
                .findAny()
                .map(alliance -> ApiMetaCode.builder()
                        .key(alliance.getKey())
                        .value(alliance.getValue())
                        .alinCode(alliance.getAlinCode())
                        .alinDomn(alliance.getAlinDomn())
                        .alinAuthDvcd(alliance.getAlinAuthDvcd())
                        .clientId(alliance.getClientId())
                        .clientSecret(alliance.getClientSecret())
                        .apiKey(alliance.getApiKey())
                        .apiVer(alliance.getApiVer())
                        .apiUri(alliance.getApiUri())
                        .build())
                .orElse(null);
        return aa;
    }
}
