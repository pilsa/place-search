package com.pilsa.place.framework.webclient;

import com.pilsa.place.framework.webclient.vo.request.RequestBase;
import com.pilsa.place.framework.webclient.vo.response.ResponseBase;
import reactor.core.publisher.Mono;

/**
 * End-Point API 요청
 *
 * @author pilsa_home1
 * @since 2021-09-11 오후 10:31
 */
public interface CommonClient {

    /**
     * Request data by get t.
     *
     * @param <T>         the type parameter
     * @param requestBase the request base
     * @param cls         the cls
     * @return the t
     */
    <T extends ResponseBase> T requestDataByGet(RequestBase requestBase, Class<T> cls);

    /**
     * Request data by get mono.
     *
     * @param <T>         the type parameter
     * @param requestBase the request base
     * @param cls         the cls
     * @return the mono
     */
    <T extends ResponseBase> Mono<T> requestDataByGetMono(RequestBase requestBase, Class<T> cls);

    //<T extends ResponseBase> T requestDataByPost(RequestBase requestBase, Class<T> cls);
}
