// src/main/java/org/myproject/client/RPCClient.java
package org.myproject.client;

import org.myproject.common.User;

import java.io.*;
import java.net.Socket;
import java.util.Random;

/**
 * RPC客户端，发送请求到服务端并接收响应
 */
public class RPCClient {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final int SERVER_PORT = 8899;

    /**
     * 客户端主方法
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 随机生成一个用户ID作为查询参数
        Integer userId = new Random().nextInt(1000);
        System.out.println("客户端：发送用户ID " + userId + " 到服务端");

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            // 发送用户ID到服务端
            oos.writeInt(userId);
            oos.flush();
            System.out.println("客户端：已发送用户ID " + userId);

            // 接收服务端返回的User对象
            User user = (User) ois.readObject();
            System.out.println("客户端：接收到服务端返回的用户信息：" + user);

        } catch (IOException | ClassNotFoundException e) {
            System.err.println("客户端出错：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
