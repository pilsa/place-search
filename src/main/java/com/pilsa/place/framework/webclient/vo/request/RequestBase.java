package com.pilsa.place.framework.webclient.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pilsa.place.common.code.VersionInfoCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@ToString
public class RequestBase {

    @Builder.Default
    @JsonIgnore
    private VersionInfoCode version = VersionInfoCode.NONE;

    @JsonIgnore
    private String baseUrl;

    @JsonIgnore
    private String uri;
}
