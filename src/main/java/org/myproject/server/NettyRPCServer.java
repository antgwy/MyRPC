package org.myproject.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.myproject.server.ServiceProvider;

import lombok.AllArgsConstructor;

/**
 * NettyRPCServer�࣬ʵ��RPCServer�ӿڣ�����ʹ��Netty��������ͨ��
 */
@AllArgsConstructor
public class NettyRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;

    @Override
    public void start(int port) {
        // Netty �������߳��飺bossGroup����������ӣ�workerGroup����������
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("Netty���������...");

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // �󶨶˿ڲ�����
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("NettyRPCServer �����������˿� " + port);

            // �ȴ��������ر�
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("NettyRPCServer ����ʧ��");
        } finally {
            // ���Źر��߳���
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        // ʵ�ַ�����ֹͣ�߼��������Ҫ��
    }
}
