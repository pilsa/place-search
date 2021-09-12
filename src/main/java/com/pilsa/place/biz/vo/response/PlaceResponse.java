package com.pilsa.place.biz.vo.response;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import com.pilsa.place.biz.client.vo.response.NaverResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@ApiModel(value="[지역검색] - 응답", description="[지역검색] API Response 입니다.")
public class PlaceResponse {

    @ApiModelProperty(value="검색 메타 데이터", example = "")
    private Meta meta;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Meta {
        @ApiModelProperty(value="총 검색 건수")
        private int totalCount;
    }

    @ApiModelProperty(value="검색된 장소 목록")
    private List<Place> places;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class Place {
        @ApiModelProperty(value="장소명,업체명")
        private String placeName;
        @ApiModelProperty(value="전체 지번 주소")
        private String addressName;
        @ApiModelProperty(value="전체 도로명 주소")
        private String roadAddressName;
        @ApiModelProperty(value="장소 상세페이지 URL")
        private String placeUrl;
    }
}
