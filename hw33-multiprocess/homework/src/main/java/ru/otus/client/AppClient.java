package ru.otus.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.generated.CalculatedValueServiceGrpc;
import ru.otus.generated.ClientReq;

import java.util.concurrent.CountDownLatch;

public class AppClient {
    private static final Logger logger = LoggerFactory.getLogger(AppClient.class);
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        ClientServiceImpl client = new ClientServiceImpl("localhost", 8190);
        client.setCountDownLatch(countDownLatch);

       CalculatedValueServiceGrpc.CalculatedValueServiceStub stub = CalculatedValueServiceGrpc
                .newStub(client.getManagedChannel());

        ClientReq clientReq = ClientReq.newBuilder()
                .setFirstValue(0)
                .setLastValue(30)
                .build();

        logger.info("client is starting...");
        stub.streamingGetValues(clientReq, client.getServerResponseObserver());

        int currentValue = 0;
        int generationValue = 0;
        for (int i = 0; i < 50; i++) {
            int gValue = client.getGeneratedValue();
            if (generationValue == gValue) {
                currentValue++;
            } else {
                generationValue = gValue;
                currentValue += generationValue + 1;
            }
            logger.info("iteration: {}, currentValue: {}, generationValue: {}", i, currentValue, generationValue);
            Thread.sleep(1000);
        }

        countDownLatch.await();

        logger.info("client shutdown");
        client.getManagedChannel().shutdown();
    }
}
