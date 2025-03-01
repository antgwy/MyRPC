package org.myproject.io;

import org.myproject.common.RPCRequest;
import org.myproject.common.RPCResponse;

import java.io.*;
import java.net.Socket;

/**
 * IOClient�࣬���������˵�ͨ��
 */
public class IOClient {
    /**
     * ����RPCRequest��ָ����������������RPCResponse
     *
     * @param host    ������������ַ
     * @param port    �������˿ں�
     * @param request RPC�������
     * @return RPC��Ӧ����
     */
    public static RPCResponse sendRequest(String host, int port, RPCRequest request) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {

            System.out.println("�ͻ��ˣ��������� " + request);
            oos.writeObject(request);
            oos.flush();

            RPCResponse response = (RPCResponse) ois.readObject();
            System.out.println("�ͻ��ˣ��յ���Ӧ " + response);
            return response;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("�ͻ��ˣ���������ʱ����");
            return null;
        }
    }
}
