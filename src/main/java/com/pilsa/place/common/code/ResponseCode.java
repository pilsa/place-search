package com.pilsa.place.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ResponseCode {
    /*======================================================================================
     * 200
    ======================================================================================*/
    SUCCESS(HttpStatus.OK,                    true  ,"SUCCESS", "성공"),
    SUCCESS_CREATED(HttpStatus.CREATED,       true  ,"SUCCESS_CREATED", "등록성공"),

    /*======================================================================================
     * 400
    ======================================================================================*/
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND,            false ,"ResourceNotFound", "요청한 자원에 대한 정보가 없습니다."),
    MISSING_REQUIRED_PARAMETERS(HttpStatus.BAD_REQUEST, false ,"MissingRequiredParameters","%s"),

    INVALID_HEADER(HttpStatus.BAD_REQUEST,              false ,"InvalidHeader", "header 필수 파라미터가 누락되었습니다. : [%s]"),

    INVALID_PARAMETER(HttpStatus.BAD_REQUEST,           false ,"InvalidParameter","파라미터 값이 올바르지 않습니다. : [%s]"),
    INVALID_PARAMETER_TYPE(HttpStatus.BAD_REQUEST,      false ,"InvalidParameterType","파라미터 타입이 올바르지 않습니다. : [%s]"),
    INVALID_PARAMETER_RANGE(HttpStatus.BAD_REQUEST,     false ,"InvalidParameterRange","[%s] 파라미터 값의 입력 범위는 [%s] 입니다."),
    INVALID_CONTENT_TYPE(HttpStatus.BAD_REQUEST,        false ,"InvalidContentType","	요청이 API가 요구하는 Content Type과 다름"),

    NOT_FOUND_API(HttpStatus.NOT_FOUND,                 false ,"NotFoundApi", "요청한 엔드포인트는 존재하지 않음(일반적인 HTTP 404 에러)"),
    API_NOT_FOUND(HttpStatus.BAD_REQUEST,               false ,"ApiNotFound","요청이 API가 요구하는 URL 혹은 HTTP 메서드와 다름"),

    /*======================================================================================
     * 500
    ======================================================================================*/
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,false , "InternalServerError", "시스템장애"),
    FAIL_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR,false , "FailRequest", "API 요청 처리 실패"),
    TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR,false , "Timeout", "처리시간 초과 에러"),
    ;

    private HttpStatus httpStatus;
    private boolean success;
    private String code;
    private String message;

}
