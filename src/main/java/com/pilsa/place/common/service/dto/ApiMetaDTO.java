package com.pilsa.place.common.service.dto;

import com.pilsa.place.common.code.ApiCode;
import com.pilsa.place.common.code.VersionInfoCode;
import com.pilsa.place.common.code.YesNoCode;
import lombok.*;

/**
 *
 * @author pilsa_home1
 * @since 2021-09-13 오전 12:28
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ApiMetaDTO {
    private ApiCode apiCode;        /* API구분코드 */
    private String apiNm;           /* API이름 */
    private String alinCode;        /* 기관구분코드 */
    private String alinDomn;        /* 기관도메인 */
    private String alinAuthDvcd;    /* 기관인증구분코드 */
    private String clientId;        /* 클라이언트ID */
    private String clientSecret;    /* 클라이언트SECRET */
    private String apiKey;          /* API키 */
    private VersionInfoCode apiVer; /* API버전 */
    private String apiUri;          /* APIURI */
}
