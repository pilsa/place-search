package com.pilsa.place.biz.client.vo.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link KakaoResponse} 와 {@link NaverResponse}가 통합된 Response 입니다.
 *
 * @author pilsa_home1
 * @since 2021-09-10 오후 10:15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MergeResponse {

    private KakaoResponse kakaoResponse;
    private NaverResponse naverResponse;
}
