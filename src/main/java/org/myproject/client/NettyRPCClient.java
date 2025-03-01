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
 * NettyRPCClient�࣬����Nettyʵ��RPCClient�ӿ�
 */
public class NettyRPCClient implements RPCClient {
    private String host;
    private int port;

    public NettyRPCClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * ����RPCRequest������RPCResponse
     *
     * @param request RPC�������
     * @return RPC��Ӧ����
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
                            // ����ճ���������
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                            pipeline.addLast(new LengthFieldPrepender(4));
                            // ʹ��Java���л�
                            pipeline.addLast(new ObjectEncoder());
                            pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)));
                            // �Զ��崦����
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

            // ���ӵ�������
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();

            // ��������
            channel.writeAndFlush(request).sync();

            // �ȴ���Ӧ
            latch.await();

            return responseHolder[0];

        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Netty�ͻ��ˣ���������ʱ����");
            return null;
        } finally {
            group.shutdownGracefully();
        }
    }
}
