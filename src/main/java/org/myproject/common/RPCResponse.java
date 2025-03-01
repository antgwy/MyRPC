package org.myproject.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * RPC响应对象，包含方法调用的结果和状态信息
 */
@Data
@Builder
public class RPCResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    // 状态码，200表示成功，500表示失败
    private int code;
    // 状态消息
    private String message;
    // 返回的数据
    private Object data;

    /**
     * 成功响应的快捷方法
     *
     * @param data 返回数据
     * @return RPCResponse对象
     */
    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).data(data).build();
    }

    /**
     * 失败响应的快捷方法
     *
     * @return RPCResponse对象
     */
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务器发生错误").build();
    }
}
