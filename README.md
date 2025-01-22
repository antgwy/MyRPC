
# MyRPC

```bash
MyRPC/
├── pom.xml
└── src
    └── main
        └── java
            └── org
                └── myproject
                    ├── Main.java
                    ├── client
                    │   ├── ClientProxy.java
                    │   └── RPCClient.java
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
                        ├── RPCServer.java
                        ├── ServiceProvider.java
                        ├── SimpleRPCRPCServer.java
                        ├── TestServer.java
                        ├── ThreadPoolRPCRPCServer.java
                        ├── UserServiceImpl.java
                        └── WorkThread.java
```

1. 公共模块 (common 包)
   * `User.java` 一个简单的POJO（Plain Old Java Object），表示用户信息。
   * `UserService.java` 定义了用户服务接口，列出客户端可以调用的方法。
   * `Blog.java` 一个简单的POJO，用于表示博客信息。
   * `BlogService.java` 定义了博客服务接口，用于处理与博客相关的操作
   * `RPCRequest.java` 定义了RPC请求的消息格式，包含需要调用的服务接口和方法信息。
   * `RPCResponse.java` 定义了RPC响应的消息格式，包含调用结果和状态信息。
2. 服务端模块（server 包）
   * `UserServiceImpl.java` UserService接口的具体实现类，提供业务逻辑。
   * `BlogServiceImpl.java` BlogService接口的具体实现类。
   * `RPCServer.java` 将 RPCServer 抽象为接口，定义启动和停止方法，使得未来可以创建不同类型的服务器实现。
   * `SimpleRPCRPCServer.java` 一个简单的阻塞I/O实现，每个客户端连接由一个独立的线程处理。
   * `ThreadPoolRPCRPCServer` 通过引入线程池，提升服务器在高并发场景下的性能，避免为每个连接创建新线程导致资源消耗过大。
   * `ServiceProvider.java` 为支持多服务接口创建的服务注册中心，用于管理服务接口与其实现类的映射。
   * `WorkThread.java` 工作任务类，处理单个客户端请求，每个工作线程负责解析请求、调用服务方法并返回响应。
   * `TestServer.java` 整合服务注册和服务器启动逻辑，通过 ServiceProvider 自动注册多个服务，并启动RPC服务器进行测试。
3. 客户端模块（client 包）
   * `IOClient.java` 底层通信类，负责与服务器建立Socket连接，发送RPCRequest并接收RPCResponse。
   * `ClientProxy.java` 使用动态代理封装RPC调用，使得客户端调用看起来像是本地方法调用。
   * `RPCClient.java` 客户端主类，使用代理对象调用远程服务的方法。


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

<div align="center"><img src="imgs/runserver.png"/></div>


## 启动客户端

```bash
mvn exec:java -Dexec.mainClass="org.myproject.client.RPCClient"
```

<div align="center"><img src="imgs/runclient.png"/></div>
