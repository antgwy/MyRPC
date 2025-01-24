package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import lombok.AllArgsConstructor;

import java.io.*;
import java.net.Socket;

/**
 * SimpleRPCClient类，基于Java Socket实现RPCClient接口
 */
@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;

    /**
     * 发送RPCRequest并接收RPCResponse
     *
     * @param request RPC请求对象
     * @return RPC响应对象
     */
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("客户端：发送请求 " + request);
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            System.out.println("客户端：接收到响应 " + response);
            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端：发送请求时出错");
            return null;
        }
    }
}
