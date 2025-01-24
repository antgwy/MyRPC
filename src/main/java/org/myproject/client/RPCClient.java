package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

/**
 * RPCClient接口，定义发送请求的方法
 */
public interface RPCClient {
    /**
     * 发送RPC请求并接收响应
     *
     * @param request RPC请求对象
     * @return RPC响应对象
     */
    RPCResponse sendRequest(RPCRequest request);
}
