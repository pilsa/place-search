package com.pilsa.invest.biz.client.vo.request;

import com.pilsa.invest.common.code.VersionInfoCode;
import com.pilsa.invest.framework.webclient.annotation.RequestParam;
import com.pilsa.invest.framework.webclient.vo.request.RequestBase;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * <pre>
 * description :
 * </pre>
 *
 * @author pilsa115
 * @since 2021-05-24 오후 6:25
 */
@Getter
@SuperBuilder
public class KakaoRequest extends RequestBase {
/*
    @RequestParam(name="search_timestamp")
    private String searchTimestamp; // 조회 타임스탬프
    @RequestParam(name="next_page", isIncludeZero = false)
    private String nextPage; // 다음 페이지 기준개체
    @RequestParam(name="limit")
    private String limit; // 최대조회갯수*/

    @RequestParam(name="query")
    private String query; // 검색을 원하는 질의어

    public KakaoRequest(VersionInfoCode versionInfoCode){
        super(versionInfoCode);
    }

}