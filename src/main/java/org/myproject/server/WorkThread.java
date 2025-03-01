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
 * ���������࣬�������ͻ�������
 */
@AllArgsConstructor
public class WorkThread implements Runnable{
    private Socket socket;
    private Map<String, Object> serviceProvide;

    @Override
    public void run() {
        try (ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // ��ȡRPCRequest
            RPCRequest request = (RPCRequest) ois.readObject();
            System.out.println("����ˣ��յ����� " + request);

            // ������������RPCResponse
            RPCResponse response = getResponse(request);

            // ������Ӧ
            oos.writeObject(response);
            oos.flush();
            System.out.println("����ˣ�������Ӧ " + response);

        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            System.out.println("����ˣ���IO�ж�ȡ���ݴ���");
        } finally {
            try {
                socket.close();
                System.out.println("����ˣ��رտͻ�������");
            } catch (IOException e) {
                System.err.println("����ˣ��رտͻ�������ʱ����" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private RPCResponse getResponse(RPCRequest request){
        // ��ȡ����ӿ���
        String interfaceName = request.getInterfaceName();
        // ��ȡ����ʵ�ֶ���
        Object service = serviceProvide.get(interfaceName);
        // ʹ�÷�����ö�Ӧ�ķ���
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("����ˣ�����ִ�д���");
            return RPCResponse.fail();
        }
    }
}
