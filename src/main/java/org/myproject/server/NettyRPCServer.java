package org.myproject.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.myproject.server.ServiceProvider;

import lombok.AllArgsConstructor;

/**
 * NettyRPCServer类，实现RPCServer接口，负责使用Netty处理网络通信
 */
@AllArgsConstructor
public class NettyRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;

    @Override
    public void start(int port) {
        // Netty 的两个线程组：bossGroup负责接受连接，workerGroup负责处理连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        System.out.println("Netty服务端启动...");

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyServerInitializer(serviceProvider))
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定端口并启动
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("NettyRPCServer 启动，监听端口 " + port);

            // 等待服务器关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("NettyRPCServer 启动失败");
        } finally {
            // 优雅关闭线程组
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        // 实现服务器停止逻辑（如果需要）
    }
}
