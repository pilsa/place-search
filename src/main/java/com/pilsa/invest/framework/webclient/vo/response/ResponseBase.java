package com.pilsa.invest.framework.webclient.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResponseBase {

    @JsonProperty("rsp_code")
    private String responseCode;
    @JsonProperty("rsp_msg")
    private String responseMessage;
}
