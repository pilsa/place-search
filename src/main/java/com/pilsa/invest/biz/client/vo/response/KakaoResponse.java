package com.pilsa.invest.biz.client.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pilsa.invest.framework.webclient.vo.response.ResponseBase;
import lombok.*;

import java.util.List;

/**
 * <pre>
 * description :
 * </pre>
 *
 * @author pilsa115
 * @since 2021-05-24 오후 6:32
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KakaoResponse extends ResponseBase {


    @JsonProperty("meta")
    private Meta meta; // 보유계좌목록

    @JsonProperty("documents")
    private List<Document> documents; // 보유계좌목록


    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Meta {

        @JsonProperty("total_count")
        private int totalCount; // 검색어에 검색된 문서 수

        @JsonProperty("pageable_count")
        private int pageableCount; // total_count 중 노출 가능 문서 수 (최대값: 45)

        @JsonProperty("is_end")
        private boolean isEnd; // 현재 페이지가 마지막 페이지인지 여부 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음

        @JsonProperty("same_name")
        private SameName sameName; // 질의어의 지역 및 키워드 분석 정보

            @AllArgsConstructor
            @NoArgsConstructor
            @Getter
            public static class SameName {
                @JsonProperty("region")
                String[] region; // 질의어에서 인식된 지역의 리스트 예: '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트

                @JsonProperty("keyword")
                private String keyword; // 질의어에서 지역 정보를 제외한 키워드 예: '중앙로 맛집' 에서 '맛집'

                @JsonProperty("selected_region")
                private String selectedRegion; // 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
            }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Document {
        @JsonProperty("id")
        private String id;
        @JsonProperty("place_name")
        private String placeName;
        @JsonProperty("category_name")
        private String categoryName;
        @JsonProperty("category_group_code")
        private String categoryGroupCode;
        @JsonProperty("category_group_name")
        private String categoryGroupName;
        @JsonProperty("phone")
        private String phone;
        @JsonProperty("address_name")
        private String addressName;
        @JsonProperty("road_address_name")
        private String roadAddressName;
        @JsonProperty("x")
        private String x;
        @JsonProperty("y")
        private String y;
        @JsonProperty("place_url")
        private String placeUrl;
        @JsonProperty("distance")
        private String distance;
        @JsonProperty("selected_region")
        private String selectedRegion;
    }
}
