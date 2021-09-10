package com.pilsa.place.biz.client.service;

import com.pilsa.place.biz.client.vo.request.KakaoRequest;
import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.framework.webclient.CommonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * description :
 * </pre>
 *
 * @author pilsa115
 * @since 2021 -05-24 오후 6:11
 */
@Service
public class KaKaoClientService {

    private CommonClient httpClient;

    /**
     * Sets accounts deposit client service.
     *
     * @param httpClient the http client
     */
    @Autowired
    @Qualifier("httpClient")
    public void setAccountListClientService(CommonClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * Get account list master inquiry account list master response.
     * API구분코드 : BA01 (계좌 목록 조회)
     *
     * @param request the request
     * @return the inquiry account list master response
     */
    public KakaoResponse searchLocal(KakaoRequest request){
         return httpClient.requestDataByGet(request, KakaoResponse.class);
    }


}
