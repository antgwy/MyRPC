package org.myproject.client;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import lombok.AllArgsConstructor;

import java.io.*;
import java.net.Socket;

/**
 * SimpleRPCClient�࣬����Java Socketʵ��RPCClient�ӿ�
 */
@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;

    /**
     * ����RPCRequest������RPCResponse
     *
     * @param request RPC�������
     * @return RPC��Ӧ����
     */
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("�ͻ��ˣ��������� " + request);
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            System.out.println("�ͻ��ˣ����յ���Ӧ " + response);
            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("�ͻ��ˣ���������ʱ����");
            return null;
        }
    }
}
