
# MyRPC

```bash
MyRPC/
├── pom.xml
└── src
    └── main
        └── java
            └── org
                └── myproject
                    ├── client
                    │   ├── NettyClientInitializer.java
                    │   ├── NettyRPCClient.java
                    │   ├── NettyRPCClientHandler.java
                    │   ├── RPCClient.java
                    │   ├── RPCClientProxy.java
                    │   ├── SimpleRPCClient.java
                    │   └── TestClient.java
                    ├── common
                    │   ├── Blog.java
                    │   ├── BlogService.java
                    │   ├── RPCRequest.java
                    │   ├── RPCResponse.java
                    │   ├── User.java
                    │   └── UserService.java
                    ├── io
                    │   └── IOClient.java
                    └── server
                        ├── BlogServiceImpl.java
                        ├── NettyRPCServer.java
                        ├── NettyRPCServerHandler.java
                        ├── NettyServerInitializer.java
                        ├── RPCServer.java
                        ├── ServiceProvider.java
                        ├── SimpleRPCRPCServer.java
                        ├── TestServer.java
                        ├── ThreadPoolRPCRPCServer.java
                        ├── UserServiceImpl.java
                        └── WorkThread.java
```

1. 公共模块 (common 包)
   * `Blog.java` 一个简单的POJO，用于表示博客信息。
   * `BlogService.java` 定义了博客服务接口，用于处理与博客相关的操作。
   * `RPCRequest.java` 定义了RPC请求的消息格式，包含需要调用的服务接口和方法信息。
   * `RPCResponse.java` 定义了RPC响应的消息格式，包含调用结果和状态信息。
   * `User.java` 一个简单的POJO（Plain Old Java Object），表示用户信息。
   * `UserService.java` 定义了用户服务接口，列出客户端可以调用的方法。
2. 服务端模块（server 包）
   * `UserServiceImpl.java` UserService接口的具体实现类，提供业务逻辑。
   * `BlogServiceImpl.java` BlogService接口的具体实现类。
   * `RPCServer.java` 将 RPCServer 抽象为接口，定义启动和停止方法，使得未来可以创建不同类型的服务器实现。
   * `SimpleRPCRPCServer.java` 一个简单的阻塞I/O实现，每个客户端连接由一个独立的线程处理。
   * `ThreadPoolRPCRPCServer` 通过引入线程池，提升服务器在高并发场景下的性能，避免为每个连接创建新线程导致资源消耗过大。
   * `ServiceProvider.java` 为支持多服务接口创建的服务注册中心，用于管理服务接口与其实现类的映射。
   * `WorkThread.java` 工作任务类，处理单个客户端请求，每个工作线程负责解析请求、调用服务方法并返回响应。
   * `NettyRPCServer.java` 实现RPCServer接口，负责使用Netty处理网络通信
   * `NettyServerInitializer.java` 负责配置 Netty 服务器的管道，包括编解码器和自定义处理器。
   * `NettyRPCServerHandler.java` 负责处理来自客户端的 RPCRequest，调用相应的服务方法，并返回 RPCResponse。
   * `TestServer.java` 整合服务注册和服务器启动逻辑，通过 ServiceProvider 自动注册多个服务，并启动RPC服务器进行测试。
3. 客户端模块（client 包）
   * `IOClient.java` 底层通信类，负责与服务器建立Socket连接，发送RPCRequest并接收RPCResponse。
   * `RPCClientProxy.java` 简化客户端对不同服务接口的调用，使用 Java 动态代理机制，将远程方法调用封装为本地接口的调用。
   * `RPCClient.java` RPC 框架的客户端需要有不同的实现方式，定义一个 RPCClient 接口，抽象出客户端的共性功能。
   * `SimpleRPCClient.java` 基于 BIO，使用传统的 Java Socket 实现通信。
   * `NettyRPCClient.java` 基于Netty，使用 Netty 框架实现高性能的非阻塞通信。Netty 客户端的实现方式是异步的，但为了与 RPCClient 接口的同步特性保持一致，使用了 CountDownLatch 来等待响应。
   * `NettyClientInitializer.java` 负责初始化 Netty 客户端的 ChannelPipeline，包括编解码器和自定义处理器。
   * `NettyRPCClientHandler.java` 负责接收来自服务器的 RPCResponse 并将其保存，以便 NettyRPCClient 可以获取响应。
   * `TestClient.java` 启动 RPC 客户端进行测试。


构建 Maven 项目

```bash
mvn clean compile
# 详细调试日志
mvn clean compile -e -X
```


## 启动服务端

```bash
mvn exec:java -Dexec.mainClass="org.myproject.server.TestServer"
```

```bash
mvn -X clean compile exec:java "-Dexec.mainClass=org.myproject.server.TestServer"
```


<div align="center"><img src="imgs/runserver.png"/></div>


## 启动客户端

```bash
mvn exec:java -Dexec.mainClass="org.myproject.client.TestClient"
```

```bash
mvn exec:java "-Dexec.mainClass=org.myproject.client.TestClient"
```

<div align="center"><img src="imgs/runclient.png"/></div>
