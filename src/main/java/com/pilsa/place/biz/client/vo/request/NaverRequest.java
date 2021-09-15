package com.pilsa.place.biz.client.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pilsa.place.common.code.ApiCode;
import com.pilsa.place.common.code.VersionInfoCode;
import com.pilsa.place.framework.webclient.annotation.RequestHeader;
import com.pilsa.place.framework.webclient.annotation.RequestParam;
import com.pilsa.place.framework.webclient.vo.request.RequestBase;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * KaKao 키워드로 장소 검색 API Request 입니다.
 *
 * @author pilsa_home1
 * @since 2021-09-10 오후 10:15
 */
@Getter
@SuperBuilder
public class NaverRequest extends RequestBase {

    @RequestParam(name="query")
    private String query; // 검색을 원하는 질의어

    @Builder.Default
    @JsonIgnore
    @RequestParam(name="display")
    private int display = 5; // 1(기본값), 5(최대) 검색 결과 출력 건수 지정

/*    @JsonIgnore
    @Builder.Default
    @RequestHeader(name = "X-Naver-Client-Id")
    private String ClientId = "bQA_gDDbrN0lWFXuhWeE";

    @JsonIgnore
    @Builder.Default
    @RequestHeader(name = "X-Naver-Client-Secret")
    private String ClientSecret = "BES5HWgEZp";*/

    public NaverRequest(VersionInfoCode versionInfoCode, String baseUrl, String uri, ApiCode.Code apiCode){
        super(versionInfoCode,baseUrl,uri,apiCode);
    }

}