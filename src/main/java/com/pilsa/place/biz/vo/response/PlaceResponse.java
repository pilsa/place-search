package com.pilsa.place.biz.vo.response;

import com.pilsa.place.biz.client.vo.response.KakaoResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The type Invest response.
 *
 * @author pilsa_home1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ApiModel(value="[지역검색] - 응답", description="[지역검색] API Response 입니다.")
public class PlaceResponse {

    @ApiModelProperty(value="상품상태" , example = "")
    private Meta meta; // 보유계좌목록

    @ApiModelProperty(value="상품상태")
    private List<Document> documents; // 보유계좌목록

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Meta {

        @ApiModelProperty(value="상품상태")
        private int totalCount; // 검색어에 검색된 문서 수

        @ApiModelProperty(value="상품상태")
        private int pageableCount; // total_count 중 노출 가능 문서 수 (최대값: 45)

        @ApiModelProperty(value="상품상태")
        private boolean isEnd; // 현재 페이지가 마지막 페이지인지 여부 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음

        @ApiModelProperty(value="상품상태")
        private KakaoResponse.Meta.SameName sameName; // 질의어의 지역 및 키워드 분석 정보

        @AllArgsConstructor
        @NoArgsConstructor
        @Getter
        public static class SameName {
            @ApiModelProperty(value="상품상태")
            String[] region; // 질의어에서 인식된 지역의 리스트 예: '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트

            @ApiModelProperty(value="상품상태")
            private String keyword; // 질의어에서 지역 정보를 제외한 키워드 예: '중앙로 맛집' 에서 '맛집'

            @ApiModelProperty(value="상품상태")
            private String selectedRegion; // 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Document {
        @ApiModelProperty(value="상품상태")
        private String id;
        @ApiModelProperty(value="상품상태")
        private String placeName;
        @ApiModelProperty(value="상품상태")
        private String categoryName;
        @ApiModelProperty(value="상품상태")
        private String categoryGroupCode;
        @ApiModelProperty(value="상품상태")
        private String categoryGroupName;
        @ApiModelProperty(value="상품상태")
        private String phone;
        @ApiModelProperty(value="상품상태")
        private String addressName;
        @ApiModelProperty(value="상품상태")
        private String roadAddressName;
        @ApiModelProperty(value="상품상태")
        private String x;
        @ApiModelProperty(value="상품상태")
        private String y;
        @ApiModelProperty(value="상품상태")
        private String placeUrl;
        @ApiModelProperty(value="상품상태")
        private String distance;
        @ApiModelProperty(value="상품상태")
        private String selectedRegion;
    }

}
