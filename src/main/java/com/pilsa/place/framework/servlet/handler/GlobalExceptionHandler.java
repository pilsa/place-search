package com.pilsa.place.framework.servlet.handler;

import com.pilsa.place.common.code.ResponseCode;
import com.pilsa.place.common.constant.ApiConstant;
import com.pilsa.place.common.web.vo.request.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author pilsa_home1
 * @since 2021-09-15 오후 5:19
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> noHandlerFoundException(NoHandlerFoundException ex) {
        /*======================================================================================
         * 애러 로그출력
        ======================================================================================*/
        log.error(ex.getMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();

        ResponseCode serverError = ResponseCode.RESOURCE_NOT_FOUND;
        /*======================================================================================
         * 트레킹을 위한 TRANSACTION_ID를 해더에 준다.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);

        StringBuilder errMsg = new StringBuilder(serverError.getMessage());
        try {
            errMsg.append("'")
                .append(ex.getHttpMethod()).append(" ")
                .append(URLDecoder.decode(ex.getRequestURL(),"utf-8"))
                .append("'");
        } catch (UnsupportedEncodingException e) {
        }

        /*======================================================================================
         * 애러응답
        ======================================================================================*/
        return new ResponseEntity(ErrorResponse.builder()
                .errorType(serverError.getCode())
                .errorMessages(errMsg.toString()).build()
                ,httpHeaders
                ,serverError.getHttpStatus());
    }
}
