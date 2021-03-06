package com.pilsa.place.framework.servlet.handler;

import com.pilsa.place.common.code.ResponseCode;
import com.pilsa.place.common.constant.ApiConstant;
import com.pilsa.place.common.web.vo.request.ErrorResponse;
import com.pilsa.place.framework.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;


/**
 * The type Rest controller handler.
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.pilsa.place.biz.controller"})
public class RestControllerHandler extends ResponseEntityExceptionHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body
            , MethodParameter returnType
            , MediaType selectedContentType
            , Class<? extends HttpMessageConverter<?>> selectedConverterType
            , ServerHttpRequest request
            , ServerHttpResponse response) {

        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();
        /*======================================================================================
         * ???????????? ?????? TRANSACTION_ID??? ????????? ??????.
        ======================================================================================*/
        response.getHeaders().add(ApiConstant.TRANSACTION_ID,transactionId);

        return body;
/*        return CommonResponse.builder()
                .success(String.valueOf(ResponseCode.SUCCESS.isSuccess()))
                .data(body)
                .build();*/
    }

    /**
     * Handle service exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<ErrorResponse> handleServiceException(ServiceException ex) {
        /*======================================================================================
         * ?????? ????????????
        ======================================================================================*/
        log.error("["+ ex.getErrorCode()+"] " + ex.getErrorMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();
        /*======================================================================================
         * ???????????? ?????? TRANSACTION_ID??? ????????? ??????.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);
        /*======================================================================================
         * ????????????
        ======================================================================================*/
        return new ResponseEntity(ErrorResponse.builder()
                .errorType(ex.getErrorCode())
                .errorMessages(ex.getErrorMessage()).build()
                ,httpHeaders
                ,ex.getHttpStatus());
    }


    /**
     * Handle exception response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        /*======================================================================================
         * ?????? ????????????
        ======================================================================================*/
        log.error(ex.getMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();

        ResponseCode serverError = ResponseCode.RESOURCE_NOT_FOUND;
        /*======================================================================================
         * ???????????? ?????? TRANSACTION_ID??? ????????? ??????.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);
        /*======================================================================================
         * ????????????
        ======================================================================================*/
        return new ResponseEntity(ErrorResponse.builder()
                .errorType(serverError.getCode())
                .errorMessages(serverError.getMessage()).build()
                ,httpHeaders
                ,serverError.getHttpStatus());
    }


    /**
     * Handle violation exception response entity.
     * hibernate.validator ??????.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleViolationException(ConstraintViolationException ex) {
        /*======================================================================================
         * ?????? ????????????
        ======================================================================================*/
        log.error(ex.getMessage(), ex);
        /*======================================================================================
         * mdcAdapter clear
        ======================================================================================*/
        String transactionId = MDC.get(ApiConstant.TRANSACTION_ID);
        MDC.clear();
        ResponseCode code = ResponseCode.MISSING_REQUIRED_PARAMETERS;
        /*======================================================================================
         * ???????????? ?????? TRANSACTION_ID??? ????????? ??????.
        ======================================================================================*/
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(ApiConstant.TRANSACTION_ID,transactionId);
        /*======================================================================================
         * ????????????
        ======================================================================================*/
        return new ResponseEntity(ErrorResponse.builder()
                .errorType(code.getCode())
                .errorMessages(getResultMessage(ex.getConstraintViolations().iterator())).build()
                ,httpHeaders
                ,code.getHttpStatus());
    }

    protected String getResultMessage(final Iterator<ConstraintViolation<?>> violationIterator) {
        final StringBuilder resultMessageBuilder = new StringBuilder();
        while (violationIterator.hasNext() == true) {
            final ConstraintViolation<?> constraintViolation = violationIterator.next();
            resultMessageBuilder
                    .append("['")
                    .append(getPopertyName(constraintViolation.getPropertyPath().toString())) // ????????? ????????? ????????? ??????
                    .append(" ??? ")
                    //.append(constraintViolation.getInvalidValue()) // ???????????? ?????? ???
                    .append(constraintViolation.getMessage()) // ????????? ?????? ?????? ??? ?????????
                    .append(".']");
            if (violationIterator.hasNext() == true) {
                resultMessageBuilder.append(", ");
            }
        }

        return resultMessageBuilder.toString();
    }
    protected String getPopertyName(String propertyPath) {
        return propertyPath.substring(propertyPath.lastIndexOf('.') + 1);
    }
}
