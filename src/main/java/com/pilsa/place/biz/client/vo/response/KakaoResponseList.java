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
@Builder
@EqualsAndHashCode(callSuper = true)
public class KakaoResponseList extends ResponseBase {

    @JsonProperty("KakaoResponseList")
    private List<KakaoResponse> KakaoResponseList;


}
