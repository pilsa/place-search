package com.pilsa.place.common.code;

import lombok.Builder;
import lombok.Getter;


/**
 * The type Alliance code.
 *
 * @author pilsa_home1
 */
@Getter
@Builder
public class ApiMetaCode implements EnumCode {

    private ApiCode.Code key;           /* API구분코드 */
    private String value;               /* API이름 */
    private String alinCode;            /* 기관구분코드 */
    private String alinDomn;            /* 기관도메인 */
    private AuthTypeCode alinAuthDvcd;  /* 기관인증구분코드 */
    private String clientId;            /* 클라이언트ID */
    private String clientSecret;        /* 클라이언트SECRET */
    private String apiKey;              /* API키 */
    private VersionInfoCode apiVer;     /* API버전 */
    private String apiUri;              /* APIURI */

}
