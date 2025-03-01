package org.myproject.client;

import org.myproject.common.User;
import org.myproject.common.UserService;
import org.myproject.common.Blog;
import org.myproject.common.BlogService;

/**
 * TestClient�࣬����RPC�ͻ��˵Ĺ���
 */
public class TestClient {
    public static void main(String[] args) {
        // ����Netty RPC�ͻ��ˣ�ָ�������������Ͷ˿�
        NettyRPCClient nettyRPCClient = new NettyRPCClient("127.0.0.1", 8899);

        // ����RPCClientProxy������NettyRPCClient
        RPCClientProxy rpcClientProxy = new RPCClientProxy(nettyRPCClient);

        // ��ȡUserService�Ĵ������
        UserService userService = rpcClientProxy.getProxy(UserService.class);

        // ��ȡBlogService�Ĵ������
        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);

        // ����UserService��Զ�̷���
        User userByUserId = userService.getUserByUserId(10);
        System.out.println("�ͻ��ˣ��ӷ���˵õ���userΪ��" + userByUserId);

        // ����UserService����һ��Զ�̷���
        User user = User.builder().userName("����").id(100).sex(true).build();
        Integer insertResult = userService.insertUserId(user);
        System.out.println("�ͻ��ˣ������˲������ݽ����" + insertResult);

        // ����BlogService��Զ�̷���
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("�ͻ��ˣ��ӷ���˵õ���blogΪ��" + blogById);
    }
}
