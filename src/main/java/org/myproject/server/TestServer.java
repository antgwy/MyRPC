package org.myproject.server;

import org.myproject.server.ServiceProvider;
import org.myproject.server.RPCServer;
import org.myproject.server.SimpleRPCRPCServer;
import org.myproject.common.UserService;
import org.myproject.common.BlogService;
import org.myproject.server.UserServiceImpl;
import org.myproject.server.BlogServiceImpl;

import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
        // 创建服务实现类实例
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        // 创建服务提供者并注册服务
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        // 获取服务映射表
        Map<String, Object> serviceMap = serviceProvider.getServiceProviderMap();

        // 创建并启动基于线程池的RPC服务器
        RPCServer rpcServer = new ThreadPoolRPCRPCServer(serviceMap);
        rpcServer.start(8899);
    }
}