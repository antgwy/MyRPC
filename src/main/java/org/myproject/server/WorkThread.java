package org.myproject.server;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import lombok.AllArgsConstructor;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * 工作任务类，处理单个客户端请求
 */
@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private Map<String, Object> serviceProvide;

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // 读取RPCRequest
            RPCRequest request = (RPCRequest) ois.readObject();
            System.out.println("服务端：收到请求 " + request);

            // 处理请求并生成RPCResponse
            RPCResponse response = getResponse(request);

            // 发送响应
            oos.writeObject(response);
            oos.flush();
            System.out.println("服务端：返回响应 " + response);

        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("服务端：从IO中读取数据错误");
        } finally {
            try {
                socket.close();
                System.out.println("服务端：关闭客户端连接");
            } catch (IOException e) {
                System.err.println("服务端：关闭客户端连接时出错：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private RPCResponse getResponse(RPCRequest request){
        // 获取服务接口名
        String interfaceName = request.getInterfaceName();
        // 获取服务实现对象
        Object service = serviceProvide.get(interfaceName);
        // 使用反射调用对应的方法
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("服务端：方法执行错误");
            return RPCResponse.fail();
        }
    }
}
