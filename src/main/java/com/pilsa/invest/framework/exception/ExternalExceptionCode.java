package com.pilsa.invest.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.EnumSet;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum ExternalExceptionCode {

    SUCCESS(HttpStatus.OK, "00000", "EMFS0000", "성공"),
    UP_TO_DATE(HttpStatus.OK, "00001", "EMFS0000", "성공(UP_TO_DATE)"),
    INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "40001", "EMFS4001", "요청 파라미터에 문제가 있는 경우"),
    INVALID_HEADER(HttpStatus.BAD_REQUEST, "40002", "EMFS4001", "헤더 값 미존재"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "40101", "EMFS4002", "유효하지 않은 접근토큰"),
    INVALID_IP(HttpStatus.UNAUTHORIZED, "40102", "EMFS4001", "허용되지 않은 원격지 IP"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "40103", "EMFS4002", "접근토큰이 폐기된 상태"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "40104", "EMFS4003", "API 사용 권한 없음 (불충분한 scope 등)"),
    UNAUTHORIZED_INQUIRY(HttpStatus.UNAUTHORIZED, "40105", "EMFS4003", "자산(계좌, 카드 등)에 대한 정보조회 권한 없음"),
    INVALID_API(HttpStatus.FORBIDDEN, "40301", "EMFS4001", "올바르지 않은 API 호출"),
    FORBIDDEN_CLIENT(HttpStatus.FORBIDDEN, "40302", "EMFS4001", "일시적으로 해당 클라이언트의 요청이 제한됨"),
    UNCONFIRMED_ALLIANCE(HttpStatus.FORBIDDEN, "40303", "EMFS4001", "기관코드 확인 불가"),
    INVALID_LIMIT(HttpStatus.FORBIDDEN, "40304", "EMFS4004", "최대 보존기간을 초과한 데이터 요청"),
    INVALID_ASSET(HttpStatus.FORBIDDEN, "40305", "EMFS4005", "자산이 유효한 상태가 아님(카드분실, 해지 등 자산의 현 상태가 정상이 아닌 경우)"),
    NOT_FOUND_API(HttpStatus.NOT_FOUND, "40401", "EMFS4001", "요청한 엔드포인트는 존재하지 않음(일반적인 HTTP 404 에러)"),
    NOT_FOUND_API_ASSET(HttpStatus.UNAUTHORIZED, "40402", "EMFS4006", "요청한 정보(예:자산, 기관정보, 전송요구 내역 등)에 대한 정보는 존재하지 않음"),
    NOT_FOUND_CUSTOMER(HttpStatus.UNAUTHORIZED, "40403", "EMFS4007", "정보주체(고객) 미존재"),
    EXPIRED_ASSET(HttpStatus.UNAUTHORIZED, "40404", "EMFS4008", "해지된 자산 (전송요구 당시에는 ‘해지’상 태가 아니었으나, 정보 요청 시 ‘해지’된 상태인 경우)"),
    EXCEED_REQUEST(HttpStatus.TOO_MANY_REQUESTS, "42901", "EMFS4009", "정보제공 요청한도 초과"),
    RESTRICT_CLIENT(HttpStatus.TOO_MANY_REQUESTS, "42902", "EMFS4010", "일시적으로 해당 클라이언트의 요청이 제한됨"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50001", "EMFS5001", "시스템장애"),
    FAIL_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "50002", "EMFS5001", "API 요청 처리 실패"),
    TIMEOUT(HttpStatus.INTERNAL_SERVER_ERROR, "50003", "EMFS5002", "처리시간 초과 에러"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "50004", "EMFS5001", "알 수 없는 에러(예비 배정)"),
    AUTH_ERROR(HttpStatus.BAD_REQUEST, "40001", "EMFS4001", "인증 오류"),
    AUTH_TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "9999", "EMFS9999", "토큰이 유효하지 않습니다."),
    ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "99999", "EMFS9998", "클라이언트 오류");

    private HttpStatus httpStatus;
    private String responseCode;
    private String metaCode;
    private String message;

    public static ExternalExceptionCode findByResponseCode(String code){

        if (StringUtils.isEmpty(code)) return ExternalExceptionCode.ERROR;
        return EnumSet.allOf(ExternalExceptionCode.class).stream()
                .filter(e -> e.getResponseCode().equals(code)).findAny().orElse(ExternalExceptionCode.ERROR);
    }
}
