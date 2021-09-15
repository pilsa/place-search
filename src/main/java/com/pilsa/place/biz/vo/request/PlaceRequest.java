package com.pilsa.place.biz.vo.request;

import com.pilsa.place.common.web.vo.request.CommonRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
@ApiModel(value="[지역검색] - 요청", description="[지역검색] API Request 입니다.")
public class PlaceRequest extends CommonRequest {

    @NotNull
    @NotBlank
    @Length(min = 1 , max = 100)
    @ApiModelProperty(value="질의어", example = "카카오프렌즈", required = true)
    private String query;

}
