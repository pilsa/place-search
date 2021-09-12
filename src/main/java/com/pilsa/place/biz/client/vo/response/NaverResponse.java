package com.pilsa.place.biz.client.vo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pilsa.place.biz.client.vo.ClientConstant;
import com.pilsa.place.framework.webclient.vo.response.ResponseBase;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;

/**
 * Naver 키워드로 장소 검색 API Response 입니다.
 * @author pilsa_home1
 * @since 2021-09-10 오후 10:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NaverResponse extends ResponseBase {

    @JsonProperty("lastBuildDate")
    private Date lastBuildDate; // 검색 결과를 생성한 시간이다.
    @JsonProperty("total")
    private int total; // 	검색 결과 문서의 총 개수를 의미한다.
    @JsonProperty("start")
    private int start; // 	검색 결과 문서 중, 문서의 시작점을 의미한다.
    @JsonProperty("display")
    private int display; // 검색된 검색 결과의 개수이다.
    @JsonProperty("category")
    private String category; // 검색 결과 업체, 기관의 분류 정보를 제공한다.
    @JsonProperty("items")
    private ArrayList<Item> items; // 개별 검색 결과

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class Item {
        @JsonProperty("title")
        private String title; // 검색 결과 업체, 기관명을 나타낸다.
        @JsonProperty("link")
        private String link; // 검색 결과 업체, 기관의 상세 정보가 제공되는 네이버 페이지의 하이퍼텍스트 link를 나타낸다.
        @JsonProperty("description")
        private String description; // 검색 결과 업체, 기관명에 대한 설명을 제공한다.
        @JsonProperty("telephone")
        private String telephone; // 빈 문자열 반환. 과거에 제공되던 항목이라 하위 호환성을 위해 존재한다.
        @JsonProperty("address")
        private String address; // 검색 결과 업체, 기관명의 주소를 제공한다.
        @JsonProperty("roadAddress")
        private String roadAddress; // 	검색 결과 업체, 기관명의 도로명 주소를 제공한다.
        @JsonProperty("mapx")
        private String mapx; // 검색 결과 업체, 기관명 위치 정보의 x좌표를 제공한다. 제공값은 카텍좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.
        @JsonProperty("mapy")
        private String mapy; // 검색 결과 업체, 기관명 위치 정보의 y좌표를 제공한다. 제공값은 카텍 좌표계 값으로 제공된다. 이 좌표값은 지도 API와 연동 가능하다.

        /*======================================================================================
         * 네이버 title의 <br></br> tag 를 제거한다.
         * getter 성능을 위하여 미리 컴파일된 패턴으로 replaceAll 한다.
        ======================================================================================*/
        public String getTitle() {
            return ClientConstant.NAVER_REX_PATTERN.matcher(this.title).replaceAll("");
        }
    }
}
