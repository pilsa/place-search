package com.pilsa.invest.framework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExternalException extends CommonException{

    private ExternalExceptionCode externalExceptionCode;
    private String responseMessage;

    public ExternalException(ExternalExceptionCode externalExceptionCode){
        this.errorCode = externalExceptionCode.getResponseCode();
        this.responseMessage = externalExceptionCode.getMessage();
        this.externalExceptionCode = externalExceptionCode;
    }

    public ExternalException(String code, String responseMessage){
        this.errorCode = code;
        this.responseMessage = responseMessage;
    }

    public ExternalException(String code, String responseMessage, ExternalExceptionCode externalExceptionCode){
        this.errorCode = code;
        this.responseMessage = responseMessage;
        this.externalExceptionCode = externalExceptionCode;
    }


    public String getResponseCode(){
        return errorCode;
    }
    public String getMessageCode(){
        return externalExceptionCode.getMetaCode();
    }

    public HttpStatus getHttpStatus(){
        return externalExceptionCode.getHttpStatus();
    }
    public String getMessage(){
        return responseMessage;
    }
}
