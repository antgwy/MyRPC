package org.myproject.io;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import java.io.*;
import java.net.Socket;

/**
 * IOClient类，负责与服务端的通信
 */
public class IOClient {
    /**
     * 发送RPCRequest到指定服务器，并接收RPCResponse
     *
     * @param host    服务器主机地址
     * @param port    服务器端口号
     * @param request RPC请求对象
     * @return RPC响应对象
     */
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("客户端：发送请求 " + request);
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            System.out.println("客户端：收到响应 " + response);
            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端：发送请求时出错");
            return null;
        }
    }
}
