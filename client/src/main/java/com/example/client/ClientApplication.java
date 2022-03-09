package com.example.client;

import com.example.client.grpc.GrpcProfileClient;
import com.example.client.grpc.impl.GrpcProfileClientImpl;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) throws InterruptedException {
        // Access a service running on the local machine on port 7777
        String target = "localhost:7777";

        // Create a communication channel to the server, known as a Channel. Channels are thread-safe
        // and reusable. It is common to create channels at the beginning of your application and reuse
        // them until the application shuts down.
        ManagedChannel channel = ManagedChannelBuilder.forTarget(target)
                // Channels are secure by default (via SSL/TLS). For the example we disable TLS to avoid
                // needing certificates.
                .usePlaintext()
                .build();
        try {
            GrpcProfileClient client = new GrpcProfileClientImpl(channel);
            client.getCurrentProfile();
            client.clientStream(10);
            client.serverStreamBlockingStub();
            client.serverStreamAsyncStub();
            client.biDirectionalStream();
        } finally {
            // ManagedChannels use resources like threads and TCP connections. To prevent leaking these
            // resources the channel should be shut down when it will no longer be used. If it may be used
            // again leave it running.
            channel.shutdownNow().awaitTermination(5, TimeUnit.SECONDS);
        }
//        SpringApplication.run(ClientApplication.class, args);
    }

}
