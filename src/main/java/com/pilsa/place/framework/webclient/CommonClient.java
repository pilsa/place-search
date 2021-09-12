package com.pilsa.place.framework.webclient;

import com.pilsa.place.framework.webclient.vo.request.RequestBase;
import com.pilsa.place.framework.webclient.vo.response.ResponseBase;
import reactor.core.publisher.Mono;

public interface CommonClient {
    //<T extends ResponseBase> T requestDataByPost(RequestBase requestBase, Class<T> cls);
    <T extends ResponseBase> T requestDataByGet(RequestBase requestBase, Class<T> cls);
    <T extends ResponseBase> Mono<T> requestDataByGetMono(RequestBase requestBase, Class<T> cls);
}
