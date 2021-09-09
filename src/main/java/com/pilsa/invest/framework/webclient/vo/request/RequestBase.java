package com.pilsa.invest.framework.webclient.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pilsa.invest.common.code.VersionInfoCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@ToString
public class RequestBase {

/*
    @JsonIgnore
    private final String mbno;
    @JsonIgnore
    private final ApiCode.Code apiCode;
    @JsonIgnore
    private final String serviceCode;
    @JsonProperty("org_code")
    @RequestParam(name="org_code")
    private String allianceCode;
    @JsonIgnore
    @Builder.Default
    private boolean isScheduled = false;
*/

    @Builder.Default
    @JsonIgnore
    private VersionInfoCode version = VersionInfoCode.NONE;
}
