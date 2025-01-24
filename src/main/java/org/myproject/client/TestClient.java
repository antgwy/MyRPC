package org.myproject.client;

import org.myproject.common.User;
import org.myproject.common.UserService;
import org.myproject.common.Blog;
import org.myproject.common.BlogService;

/**
 * TestClient类，测试RPC客户端的功能
 */
public class TestClient {
    public static void main(String[] args) {
        // 创建Netty RPC客户端，指定服务器主机和端口
        NettyRPCClient nettyRPCClient = new NettyRPCClient("127.0.0.1", 8899);

        // 创建RPCClientProxy并传入NettyRPCClient
        RPCClientProxy rpcClientProxy = new RPCClientProxy(nettyRPCClient);

        // 获取UserService的代理对象
        UserService userService = rpcClientProxy.getProxy(UserService.class);

        // 获取BlogService的代理对象
        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);

        // 调用UserService的远程方法
        User userByUserId = userService.getUserByUserId(10);
        System.out.println("客户端：从服务端得到的user为：" + userByUserId);

        // 调用UserService的另一个远程方法
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer insertResult = userService.insertUserId(user);
        System.out.println("客户端：向服务端插入数据结果：" + insertResult);

        // 调用BlogService的远程方法
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("客户端：从服务端得到的blog为：" + blogById);
    }
}
