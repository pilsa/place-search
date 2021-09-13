package com.pilsa.place.biz.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author pilsa_home1
 * @since 2021-09-11 오후 10:58
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(value="[인기 키워드 목록 조회] - 응답", description="[인기 키워드 목록 조회] API Response 입니다.")
public class KeywordResponse implements Serializable {

    @ApiModelProperty(value="메타데이터", example = "")
    private Meta meta;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Meta implements Serializable {
        @ApiModelProperty(value="총검색건수")
        private int totalCount;
    }

    @ApiModelProperty(value="키워드목록")
    private List<Keyword> keywords;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Keyword implements Serializable {

        @ApiModelProperty(value="질의어")
        private String query;
        @ApiModelProperty(value="누적조회횟수")
        private int queryCnt;
    }
}
