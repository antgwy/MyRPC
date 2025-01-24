package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.codec.serialization.ClassResolvers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * NettyRPCClient类，基于Netty实现RPCClient接口
 */
public class NettyRPCClient implements RPCClient {
    private String host;
    private int port;

    public NettyRPCClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 发送RPCRequest并接收RPCResponse
     *
     * @param request RPC请求对象
     * @return RPC响应对象
     */
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        EventLoopGroup group = new NioEventLoopGroup();
        final RPCResponse[] responseHolder = new RPCResponse[1];
        final CountDownLatch latch = new CountDownLatch(1);

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            // 处理粘包拆包问题
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            // 使用Java序列化
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            // 自定义处理器
                            pipeline.addLast(new SimpleChannelInboundHandler<RPCResponse>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, RPCResponse msg) throws Exception {
                                    responseHolder[0] = msg;
                                    latch.countDown();
                                }

                                @Override
                                public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
                                    cause.printStackTrace();
                                    ctx.close();
                                    latch.countDown();
                                }
                            });
                        }
                    });

            // 连接到服务器
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            // 发送请求
            channel.writeAndFlush(request).sync();

            // 等待响应
            latch.await();

            return responseHolder[0];

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Netty客户端：发送请求时出错");
            return null;
        } finally {
            group.shutdownGracefully();
        }
    }
}
