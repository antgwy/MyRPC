package org.myproject.server;

import org.myproject.common.UserService;
import org.myproject.common.BlogService;

import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
        // ��������ʵ����ʵ��
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        // ���������ṩ�߲�ע�����
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        // ��ȡ����ӳ���
        Map<String, Object> serviceMap = serviceProvider.getServiceProviderMap();

        // ���������������̳߳ص�RPC������
        // RPCServer rpcServer = new ThreadPoolRPCRPCServer(serviceMap);
        RPCServer rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(8899);
    }
}