package org.myproject.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * ���ڴ�ͳBIO��RPC������ʵ��
 */
public class SimpleRPCRPCServer implements RPCServer {
    private Map<String, Object> serviceProvide;

    public SimpleRPCRPCServer(Map<String, Object> serviceProvide){
        this.serviceProvide = serviceProvide;
    }

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("����������ˣ������˿� " + port);
            while (true){
                Socket socket = serverSocket.accept();
                // �½�һ��WorkThread��������
                new Thread(new WorkThread(socket, serviceProvide)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("����������ʧ��");
        }
    }

    @Override
    public void stop() {
        // ʵ�ַ�����ֹͣ�߼�����ر�ServerSocket�ȣ�
    }
}
