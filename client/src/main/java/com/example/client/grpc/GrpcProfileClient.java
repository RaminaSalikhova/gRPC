package com.example.client.grpc;

public interface GrpcProfileClient {
    void getCurrentProfile();

    void clientStream(int times);

    void serverStreamBlockingStub();

    void serverStreamAsyncStub();

    void biDirectionalStream();
}
