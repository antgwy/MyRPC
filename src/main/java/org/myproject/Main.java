package org.myproject;

public class Main {
    public static void main(String[] args) {
        // 根据参数决定启动服务端还是客户端
        if (args.length == 0) {
            System.out.println("请指定启动模式：server 或 client");
            return;
        }

        switch (args[0].toLowerCase()) {
            case "server":
                org.myproject.server.RPCServer.main(args);
                break;
            case "client":
                org.myproject.client.RPCClient.main(args);
                break;
            default:
                System.out.println("未知的启动模式，请使用 server 或 client");
        }
    }
}
