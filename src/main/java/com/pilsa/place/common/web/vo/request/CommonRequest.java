package com.pilsa.place.common.web.vo.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The type Common request.
 * 공통 요청 (모든 Request VO는 상속받아서 사용한다.)
 *
 * @author pilsa_home1
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CommonRequest {

    @ApiModelProperty(hidden = true)
    private String transactionId;
}
