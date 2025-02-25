package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RPCClientProxy类，使用动态代理生成Service接口的代理对象
 */
@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {
    private RPCClient rpcClient;

    /**
     * 动态代理方法，每次代理对象调用方法都会触发此方法
     *
     * @param proxy  代理对象
     * @param method 被调用的方法
     * @param args   方法参数
     * @return 方法的返回值
     * @throws Throwable 异常
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建RPCRequest对象
        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes())
                .build();

        // 发送请求并接收响应
        RPCResponse response = rpcClient.sendRequest(request);
        if (response == null) {
            throw new RuntimeException("RPC调用失败，未收到响应");
        }
        if (response.getCode() != 200) {
            throw new RuntimeException("RPC调用失败，错误信息：" + response.getMessage());
        }
        return response.getData();
    }

    /**
     * 获取接口的代理对象
     *
     * @param clazz 服务接口的Class对象
     * @param <T>   泛型类型
     * @return 代理对象
     */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                clazz.getClassLoader(),
                new Class[]{clazz},
                this
        );
    }
}
