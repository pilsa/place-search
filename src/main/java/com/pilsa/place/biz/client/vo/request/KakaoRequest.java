package com.pilsa.place.biz.client.vo.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class KakaoRequest extends RequestBase {

    @RequestParam(name="query")
    private String query; // 검색을 원하는 질의어

    @JsonIgnore
    @Builder.Default
    @RequestParam(name="size")
    private int size = 5; // 한 페이지에 보여질 문서의 개수 1~15 사이의 값 (기본값: 15)

    @JsonIgnore
    @Builder.Default
    @RequestHeader(name = "Authorization")
    private String Authorization = "KakaoAK 1bff160ca1074e50c07585b91e47806b";

    public KakaoRequest(VersionInfoCode versionInfoCode, String baseUrl, String uri){
        super(versionInfoCode,baseUrl,uri);
    }

}