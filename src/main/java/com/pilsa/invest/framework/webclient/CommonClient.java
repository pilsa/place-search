package com.pilsa.invest.framework.webclient;

import com.pilsa.invest.framework.webclient.vo.request.RequestBase;
import com.pilsa.invest.framework.webclient.vo.response.ResponseBase;

public interface CommonClient {
    //<T extends ResponseBase> T requestDataByPost(RequestBase requestBase, Class<T> cls);
    <T extends ResponseBase> T requestDataByGet(RequestBase requestBase, Class<T> cls);
}
