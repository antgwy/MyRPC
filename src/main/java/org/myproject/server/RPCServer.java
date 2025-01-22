package org.myproject.server;

public interface RPCServer {
    void start(int port);
    void stop();
}