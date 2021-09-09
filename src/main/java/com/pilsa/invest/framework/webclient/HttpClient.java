package com.pilsa.invest.framework.webclient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pilsa.invest.common.constant.ApiConstant;
import com.pilsa.invest.framework.exception.ExternalException;
import com.pilsa.invest.framework.exception.ExternalExceptionCode;
import com.pilsa.invest.framework.webclient.annotation.PathParam;
import com.pilsa.invest.framework.webclient.annotation.RequestHeader;
import com.pilsa.invest.framework.webclient.annotation.RequestParam;
import com.pilsa.invest.framework.webclient.vo.request.RequestBase;
import com.pilsa.invest.framework.webclient.vo.response.ResponseBase;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service("httpClient")
@Slf4j
public class HttpClient implements CommonClient{

/*
    @Value("${finnq.my-data.proxy.host}")
    private String proxyHost;

    @Value("${finnq.my-data.proxy.port}")
    private int proxyPort;

    @Value("${finnq.my-data.domain-code}")
    private String domainCode;*/

    public HttpClient() {

    }

    private <T> List<Field> getAllFields(T t){
        List<Field> fields = new ArrayList<>();
        Class clazz = t.getClass();

        while(clazz != Object.class){
            fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fields;
    }

    /**
     * 데이터 요청(GET)
     * @param requestBase
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ResponseBase> T requestDataByGet(RequestBase requestBase, Class<T> cls){
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        long requestTime = System.currentTimeMillis();

        return WebClient.builder().baseUrl("https://dapi.kakao.com")
                .build()
                .get()
                .uri(uriBuild -> uriBuild.path(replaceUri(requestBase)).queryParams(getValueMap(requestBase)).build())

                .headers(httpHeaders -> {
                    //httpHeaders.setBearerAuth(getAccessToken(requestBase));
                    httpHeaders.set("Authorization", "KakaoAK 1bff160ca1074e50c07585b91e47806b");
                    setHeaders(requestBase, httpHeaders);
                })

                .exchange()
                .onErrorResume(e-> {
                    log.error("[" + transactionId + "] " + e.toString(), e);
                    //loggingApiRequest(transactionId, transactionId, requestTime, HttpStatus.SERVICE_UNAVAILABLE.value(), requestBase);
                    return Mono.error(new ExternalException(ExternalExceptionCode.ERROR));
                })
                .flatMap(clientResponse -> clientResponse.bodyToMono(cls).map(t -> {
                    logApis(transactionId, requestBase, t);
                    //loggingApiRequest(transactionId, transactionId, requestTime, clientResponse.statusCode().value(), requestBase);

/*                    if(!ExternalExceptionCode.SUCCESS.getResponseCode().equals(t.getResponseCode())) {
                        throw new ExternalException(t.getResponseCode(), t.getResponseMessage(), ExternalExceptionCode.findByResponseCode(t.getResponseCode()));
                    }*/

                    return t;
                }))
                .block();
    }

    /**
     * 데이터 요청(GET)
     * @param requestBase
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ResponseBase> T requestDataByGet2(RequestBase requestBase, Class<T> cls){
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        long requestTime = System.currentTimeMillis();

        return WebClient.builder().baseUrl("https://dapi.kakao.com")
                .build()
                .get()
                .uri(uriBuild -> uriBuild.path(replaceUri(requestBase)).queryParams(getValueMap(requestBase)).build())

                .headers(httpHeaders -> {
                    //httpHeaders.setBearerAuth(getAccessToken(requestBase));
                    httpHeaders.set("Authorization", "KakaoAK 1bff160ca1074e50c07585b91e47806b");
                    setHeaders(requestBase, httpHeaders);

                })

                .exchange()
                .onErrorResume(e-> {
                    log.error("[" + transactionId + "] " + e.toString(), e);
                    //loggingApiRequest(transactionId, transactionId, requestTime, HttpStatus.SERVICE_UNAVAILABLE.value(), requestBase);
                    return Mono.error(new ExternalException(ExternalExceptionCode.ERROR));
                })
                .flatMap(clientResponse -> clientResponse.bodyToMono(cls).map(t -> {
                    logApis(transactionId, requestBase, t);
                    //loggingApiRequest(transactionId, transactionId, requestTime, clientResponse.statusCode().value(), requestBase);

                    if(!ExternalExceptionCode.SUCCESS.getResponseCode().equals(t.getResponseCode())) {
                        throw new ExternalException(t.getResponseCode(), t.getResponseMessage(), ExternalExceptionCode.findByResponseCode(t.getResponseCode()));
                    }

                    return t;
                }))
                .block();
    }

    /**
     * 업권별 URI 셋팅
     * @param requestBase
     * @return
     */
    private String replaceUri(RequestBase requestBase){

        //String uri = apiCodeService.getValueByKey(requestBase.getApiCode());
        String uri = "local/search/keyword.json";

        for(Field field : getAllFields(requestBase)){

            PathParam pathParam = field.getAnnotation(PathParam.class);

            if(pathParam != null && !"".equals(pathParam.name())){
                try {
                    field.setAccessible(true);
                    uri = uri.replace(pathParam.name(), (String) field.get(requestBase));
                    field.setAccessible(false);
                } catch (IllegalAccessException e) {
                    field.setAccessible(false);
                    log.error("Replace Url Error {}", e.getLocalizedMessage());
                }
            }
        }
        //return requestBase.getVersion().getVersion() + "/".concat(domainCode) + uri;
        String rtnUri = requestBase.getVersion().getVersion() + "/".concat(uri);
        return rtnUri;
    }

    /**
     * 파라미터 셋팅
     * @param requestBase
     * @return
     */
    private MultiValueMap<String, String> getValueMap(RequestBase requestBase){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        for(Field field : getAllFields(requestBase)){
            RequestParam requestParam = field.getAnnotation(RequestParam.class);

            try {
                field.setAccessible(true);
                if(requestParam != null && !"".equals(requestParam.name()) && (requestParam.isIncludeZero() || (field.get(requestBase) != null && !"0".equals(String.valueOf(field.get(requestBase)))))){
                    params.add(requestParam.name(), requestParam.exceptValue() ? "" : String.valueOf(field.get(requestBase)));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                field.setAccessible(false);
                log.error("Set Parameter Error {}", e.getLocalizedMessage());
            }
        }

        return params;
    }

/*    private void loggingApiRequest(String transactionId, String globalId, long requestTime, int responseCode, RequestBase requestBase){

        apiLogService.saveApiRequest(ApiLogRequest.builder()
                .mbno(requestBase.getMbno())
                .allianceCode(requestBase.getAllianceCode())
                .serviceCode(requestBase.getServiceCode())
                .apiCode(requestBase.getApiCode())
                .globalId(globalId)
                .transactionId(transactionId)
                .responseCode(responseCode)
                .requestDateTime(requestTime)
                .responseDateTime(System.currentTimeMillis())
                .transmissionTypeCode(requestBase.isScheduled() ? "02": "01")
                .build());
    }*/

    private void logApis(String globalId, RequestBase request, ResponseBase response){
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setDefaultPropertyInclusion(JsonInclude.Include.NON_DEFAULT);
        try {
            MDC.put("globalId", globalId);
            MDC.put("request", objectMapper.writeValueAsString(request));
            MDC.put("response", objectMapper.writeValueAsString(response));

            log.info("apiLog");

            MDC.clear();

        } catch (JsonProcessingException ignore) {
        }
    }

    /**
     * 헤더 셋팅
     * @param requestBase
     * @param httpHeaders
     */
    private void setHeaders(RequestBase requestBase, HttpHeaders httpHeaders){

        for(Field field : getAllFields(requestBase)){

            RequestHeader requestParam = field.getAnnotation(RequestHeader.class);

            try {
                field.setAccessible(true);
                if(requestParam != null && !"".equals(requestParam.name())){
                    httpHeaders.set(requestParam.name(), String.valueOf(field.get(requestBase)));
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                field.setAccessible(false);
                log.error("Set Header Error {}", e.getLocalizedMessage());
            }
        }

    }

}
