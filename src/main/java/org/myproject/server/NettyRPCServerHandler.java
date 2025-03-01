package org.myproject.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * NettyRPCServerHandler�࣬����RPC���󲢷�����Ӧ
 */
@AllArgsConstructor
public class NettyRPCServerHandler extends SimpleChannelInboundHandler<RPCRequest> {
    private ServiceProvider serviceProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCRequest request) throws Exception {
        System.out.println("����ˣ��յ����� " + request);
        RPCResponse response = getResponse(request);
        ctx.writeAndFlush(response);
        System.out.println("����ˣ��ѷ�����Ӧ " + response);
    }

    /**
     * ����RPCRequest������RPCResponse
     *
     * @param request RPC�������
     * @return RPC��Ӧ����
     */
    private RPCResponse getResponse(RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        if (service == null) {
            System.out.println("����ˣ�δ�ҵ���Ӧ�ķ��� " + interfaceName);
            return RPCResponse.builder()
                    .code(404)
                    .message("Service not found: " + interfaceName)
                    .build();
        }

        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
            Object result = method.invoke(service, request.getParams());
            return RPCResponse.success(result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("����ˣ�����ִ�д���");
            return RPCResponse.builder()
                    .code(500)
                    .message("Method execution error: " + e.getMessage())
                    .build();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
