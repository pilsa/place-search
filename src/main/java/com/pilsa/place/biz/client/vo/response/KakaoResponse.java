package com.pilsa.place.biz.client.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pilsa.place.framework.webclient.vo.response.ResponseBase;
import lombok.*;

import java.util.List;

/**
 * KaKao 키워드로 장소 검색 API Response 입니다.
 * @author pilsa_home1
 * @since 2021-09-10 오후 10:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class KakaoResponse extends ResponseBase {

    @JsonProperty("meta")
    private Meta meta; // 장소 메타데이터

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Meta {

        @JsonProperty("total_count")
        private int totalCount; // 검색어에 검색된 문서 수
        @JsonProperty("pageable_count")
        private int pageableCount; // total_count 중 노출 가능 문서 수 (최대값: 45)
        @JsonProperty("is_end")
        private boolean isEnd; // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
        @JsonProperty("same_name")
        private SameName sameName; // 질의어의 지역 및 키워드 분석 정보

            @AllArgsConstructor
            @NoArgsConstructor
            @Getter
            public static class SameName {
                @JsonProperty("region")
                String[] region; // 질의어에서 인식된 지역의 리스트, ex) '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트
                @JsonProperty("keyword")
                private String keyword; // 질의어에서 지역 정보를 제외한 키워드 예: '중앙로 맛집' 에서 '맛집'
                @JsonProperty("selected_region")
                private String selectedRegion; // 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보
            }
    }

    @JsonProperty("documents")
    private List<Document> documents;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Document {
        @JsonProperty("id")
        private String id; // 장소 ID
        @JsonProperty("place_name")
        private String placeName; // 장소명, 업체명
        @JsonProperty("category_name")
        private String categoryName; // 카테고리 이름
        @JsonProperty("category_group_code")
        private String categoryGroupCode; // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
        @JsonProperty("category_group_name")
        private String categoryGroupName; // 중요 카테고리만 그룹핑한 카테고리 그룹명
        @JsonProperty("phone")
        private String phone; // 전화번호
        @JsonProperty("address_name")
        private String addressName; // 전체 지번 주소
        @JsonProperty("road_address_name")
        private String roadAddressName; // 전체 도로명 주소
        @JsonProperty("x")
        private String x; // X 좌표값, 경위도인 경우 longitude (경도)
        @JsonProperty("y")
        private String y; // Y 좌표값, 경위도인 경우 latitude (위도)
        @JsonProperty("place_url")
        private String placeUrl; // 장소 상세페이지 URL
        @JsonProperty("distance")
        private String distance; // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter
    }
}
