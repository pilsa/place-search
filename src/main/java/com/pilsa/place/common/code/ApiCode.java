package com.pilsa.place.common.code;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApiCode implements EnumCode {

    private final Code key;
    private final String value;

    @Getter
    public enum Code{
        KAKAO_SE01, // 카카오 키워드로 장소 검색 API CODE
        NAVER_SE01; // 네이버 장소 검색 API CODE
    }
}
