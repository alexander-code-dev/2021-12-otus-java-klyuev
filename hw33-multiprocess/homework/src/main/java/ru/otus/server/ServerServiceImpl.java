package ru.otus.server;

import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.generated.CalculatedValueServiceGrpc;
import ru.otus.generated.ClientReq;
import ru.otus.generated.ServerRes;

public class ServerServiceImpl extends CalculatedValueServiceGrpc.CalculatedValueServiceImplBase {
    private static final Logger logger = LoggerFactory.getLogger(ServerServiceImpl.class);
    public void streamingGetValues(ClientReq request, StreamObserver<ServerRes> responseObserver) {
        int first = request.getFirstValue();
        int last = request.getLastValue();
        for (int i  = first; i < last; i++) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                logger.warn("error sleep - ", e);
            }
            ServerRes serverRes = ServerRes.newBuilder()
                    .setGeneratedValue(i+1)
                    .build();
            responseObserver.onNext(serverRes);
            logger.info("server returned value: {}", i+1);
        }
        responseObserver.onCompleted();
    }
}
