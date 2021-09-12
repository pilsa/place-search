package com.pilsa.place.biz.client.vo;

import java.util.regex.Pattern;

/**
 * @author pilsa_home1
 * @since 2021-09-11 오후 7:48
 */
public class ClientConstant {
    public final static String NAVER_REX = "(\\<[bB]\\>|\\<\\/[bB]\\>)";
    public final static Pattern NAVER_REX_PATTERN = Pattern.compile(NAVER_REX);
}
