package org.myproject.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.*;

/**
 * 基于线程池的RPC服务器实现
 */
public class ThreadPoolRPCRPCServer implements RPCServer {
    private final ThreadPoolExecutor threadPool;
    private Map<String, Object> serviceProvide;

    public ThreadPoolRPCRPCServer(Map<String, Object> serviceProvide){
        threadPool = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                1000,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100)
        );
        this.serviceProvide = serviceProvide;
    }

    public ThreadPoolRPCRPCServer(Map<String, Object> serviceProvide, int corePoolSize,
                                  int maximumPoolSize,
                                  long keepAliveTime,
                                  TimeUnit unit,
                                  BlockingQueue<Runnable> workQueue){

        threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.serviceProvide = serviceProvide;
    }

    @Override
    public void start(int port) {
        System.out.println("服务端启动了，监听端口 " + port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(true){
                Socket socket = serverSocket.accept();
                threadPool.execute(new WorkThread(socket, serviceProvide));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        threadPool.shutdown();
    }
}
