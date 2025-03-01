package org.myproject.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.myproject.common.RPCResponse;

import io.netty.util.AttributeKey;

/**
 * NettyRPCClientHandler类，处理来自服务器的RPC响应
 */
public class NettyRPCClientHandler extends SimpleChannelInboundHandler<RPCResponse> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCResponse msg) throws Exception {
        // 将RPCResponse存储到channel的属性中，供客户端读取
        AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
        ctx.channel().attr(key).set(msg);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
