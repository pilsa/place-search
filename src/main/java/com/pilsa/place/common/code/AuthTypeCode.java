package com.pilsa.place.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * The enum Product status code.
 *
 * @author pilsa_home1
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum AuthTypeCode {

    API_KEY,
    ID_SECRET;

    private String key;
    private String value;

}
