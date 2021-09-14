package com.pilsa.place.biz.vo.request;

import com.pilsa.place.common.web.vo.request.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;

/**
 *
 * @author pilsa_home1
 * @since 2021-09-14 오전 1:53
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ApiModel(value="[인기 키워드 목록 조회] - 요청", description="[인기 키워드 목록 조회] API Request 입니다.")
public class KeywordRequest extends CommonRequest {

    @Range(min = 1 , max = 24)
    @Builder.Default
    @ApiModelProperty(value="시간범위", example = "24")
    private String hourRange = "24";

}
