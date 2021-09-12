package com.pilsa.place.biz.client.vo.response;

import com.pilsa.place.biz.vo.response.PlaceResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

/**
 * KaKao 키워드로 장소 검색 API Response 입니다.
 * @author pilsa_home1
 * @since 2021-09-10 오후 10:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MergeSimpleResponse {

    private List<MergePlace> kakaoResponse;
    private List<MergePlace> naverResponse;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class MergePlace {
        private String placeName;
        private String addressName;
        private String roadAddressName;
        private String placeUrl;
    }

}
