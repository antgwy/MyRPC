package org.myproject.client;

import org.myproject.common.User;
import org.myproject.common.UserService;

/**
 * RPCClient类，客户端的主入口
 */
public class RPCClient {
    public static void main(String[] args) {
        // 创建客户端代理，指定服务器的主机和端口
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        // 服务的方法1：获取用户信息
        User userByUserId = proxy.getUserByUserId(10);
        System.out.println("客户端：从服务端得到的user为：" + userByUserId);

        // 服务的方法2：插入用户数据
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer insertResult = proxy.insertUserId(user);
        System.out.println("客户端：向服务端插入数据结果：" + insertResult);
    }
}
