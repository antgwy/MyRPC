package org.myproject.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * RPC请求对象，包含调用所需的信息
 */
@Data
@Builder
public class RPCRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    // 服务类名，客户端只知道接口名，在服务端中用接口名指向实现类
    private String interfaceName;
    // 方法名
    private String methodName;
    // 参数列表
    private Object[] params;
    // 参数类型
    private Class<?>[] paramsTypes;
}
