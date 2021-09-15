package com.pilsa.place.common.web.vo.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The type Error response.
 * 애러를 응답할때 사용한다.
 *
 * @author pilsa_home1
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ErrorResponse {
    private String errorType;
    private String errorMessages;
}
