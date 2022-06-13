package ru.otus.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.generated.ServerRes;

import java.util.concurrent.CountDownLatch;

public class ClientServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);
    private final ManagedChannel managedChannel;
    private int generatedValue;
    private CountDownLatch countDownLatch;

    public ClientServiceImpl(String host, int port) {
        this.managedChannel = ManagedChannelBuilder
                .forAddress(host, port)
                .usePlaintext()
                .build();
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public StreamObserver<ServerRes> getServerResponseObserver() {
        return new StreamObserver<>() {
            @Override
            public void onNext(ServerRes value) {
                generatedValue = value.getGeneratedValue();
                logger.info("new value: {}", generatedValue);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("error in streamObserver - ", t);
            }

            @Override
            public void onCompleted() {
                logger.info("request completed");
                countDownLatch.countDown();
            }
        };
    }

    public ManagedChannel getManagedChannel() {
        return managedChannel;
    }

    public int getGeneratedValue() {
        return generatedValue;
    }
}
