package org.myproject.server;

import org.myproject.common.User;
import org.myproject.common.UserService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * RPC服务器，监听客户端请求并处理
 */
public class RPCServer {
    private static final int PORT = 8899;
    private final UserService userService;

    public RPCServer(UserService userService) {
        this.userService = userService;
    }

    /**
     * 启动服务器，开始监听客户端连接
     */
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("服务端启动，监听端口 " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("服务端：接收到一个客户端连接");
                // 为每个客户端连接开启一个新线程处理
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("服务端启动失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 处理客户端请求
     *
     * @param clientSocket 客户端Socket
     */
    private void handleClient(Socket clientSocket) {
        try (ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(clientSocket.getInputStream())) {

            // 读取客户端传来的用户ID
            Integer userId = ois.readInt();
            System.out.println("服务端：收到用户ID " + userId + " 的查询请求");

            // 调用UserService获取用户信息
            User user = userService.getUserByUserId(userId);

            // 将用户信息写回客户端
            oos.writeObject(user);
            oos.flush();
            System.out.println("服务端：已返回用户信息");

        } catch (IOException e) {
            System.err.println("服务端处理客户端请求时出错：" + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
                System.out.println("服务端：关闭客户端连接");
            } catch (IOException e) {
                System.err.println("服务端关闭客户端连接时出错：" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * 主方法，启动RPC服务器
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        RPCServer rpcServer = new RPCServer(userService);
        rpcServer.start();
    }
}
