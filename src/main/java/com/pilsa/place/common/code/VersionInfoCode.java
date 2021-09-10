package com.pilsa.place.common.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum VersionInfoCode {

    V1("/v1", "v1"),
    V2("/v2", "v2"),
    NONE("", "none");

    private String version;
    private String desc;
}
