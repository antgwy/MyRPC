package org.myproject.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

/**
 * 基于传统BIO的RPC服务器实现
 */
public class SimpleRPCRPCServer implements RPCServer {
    private Map<String, Object> serviceProvide;

    public SimpleRPCRPCServer(Map<String, Object> serviceProvide){
        this.serviceProvide = serviceProvide;
    }

    @Override
    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("服务端启动了，监听端口 " + port);
            while (true){
                Socket socket = serverSocket.accept();
                // 新建一个WorkThread处理请求
                new Thread(new WorkThread(socket, serviceProvide)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    @Override
    public void stop() {
        // 实现服务器停止逻辑（如关闭ServerSocket等）
    }
}
