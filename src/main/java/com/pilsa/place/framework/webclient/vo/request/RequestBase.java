package com.pilsa.place.framework.webclient.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pilsa.place.common.code.ApiCode;
import com.pilsa.place.common.code.VersionInfoCode;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * 업무에 정의하는 모든 RequestVO는 해당 {@link RequestBase}를 상속 받아 확장해야 한다.
 * {@link com.pilsa.place.framework.webclient.HttpClient} 참조.
 *
 * @author pilsa_home1
 * @since 2021-09-14 오후 11:40
 * @See com.pilsa.place.biz.client.vo.request.KakaoRequest
 */
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

    @JsonIgnore
    private final ApiCode.Code apiCode;
}
