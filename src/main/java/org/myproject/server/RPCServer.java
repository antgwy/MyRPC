package org.myproject.server;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;
import org.myproject.common.UserService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                // 使用线程池处理客户端请求（改进点）
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

            // 读取客户端传来的RPCRequest
            RPCRequest request = (RPCRequest) ois.readObject();
            System.out.println("服务端：收到请求 " + request);

            // 通过反射调用相应的方法
            Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object result = method.invoke(userService, request.getParams());

            // 封装RPCResponse并返回给客户端
            RPCResponse response = RPCResponse.success(result);
            oos.writeObject(response);
            oos.flush();
            System.out.println("服务端：返回响应 " + response);

        } catch (IOException | ClassNotFoundException | NoSuchMethodException |
                 IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("服务端：处理客户端请求时出错");
            try {
                ObjectOutputStream oos = new ObjectOutputStream(clientSocket.getOutputStream());
                RPCResponse response = RPCResponse.fail();
                oos.writeObject(response);
                oos.flush();
            } catch (IOException ioException) {
                ioException.printStackTrace();
                System.out.println("服务端：发送错误响应时出错");
            }
        } finally {
            try {
                clientSocket.close();
                System.out.println("服务端：关闭客户端连接");
            } catch (IOException e) {
                System.err.println("服务端：关闭客户端连接时出错：" + e.getMessage());
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