package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

/**
 * RPCClient�ӿڣ����巢������ķ���
 */
public interface RPCClient {
    /**
     * ����RPC���󲢽�����Ӧ
     *
     * @param request RPC�������
     * @return RPC��Ӧ����
     */
    RPCResponse sendRequest(RPCRequest request);
}
