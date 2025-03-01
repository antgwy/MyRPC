package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * RPCClientProxy�࣬ʹ�ö�̬��������Service�ӿڵĴ������
 */
@AllArgsConstructor
public class RPCClientProxy implements InvocationHandler {
    private RPCClient rpcClient;

    /**
     * ��̬��������ÿ�δ��������÷������ᴥ���˷���
     *
     * @param proxy  �������
     * @param method �����õķ���
     * @param args   ��������
     * @return �����ķ���ֵ
     * @throws Throwable �쳣
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // ����RPCRequest����
        RPCRequest request = RPCRequest.builder()
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsTypes(method.getParameterTypes())
                .build();

        // �������󲢽�����Ӧ
        RPCResponse response = rpcClient.sendRequest(request);
        if (response == null) {
            throw new RuntimeException("RPC����ʧ�ܣ�δ�յ���Ӧ");
        }
        if (response.getCode() != 200) {
            throw new RuntimeException("RPC����ʧ�ܣ�������Ϣ��" + response.getMessage());
        }
        return response.getData();
    }

    /**
     * ��ȡ�ӿڵĴ������
     *
     * @param clazz ����ӿڵ�Class����
     * @param <T>   ��������
     * @return �������
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
