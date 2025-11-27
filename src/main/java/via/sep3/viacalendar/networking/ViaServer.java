package via.sep3.viacalendar.networking;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ViaServer {
    private static final Logger log = LoggerFactory.getLogger(ViaServer.class);
    private static final int PORT = 6032;
    private final ViaCalendarMainHandler mainHandler;
    private Server grpcServer;

    public ViaServer(ViaCalendarMainHandler mainHandler) {
        this.mainHandler = mainHandler;
    }
    public void start() {
        Thread grpcThread = new Thread(() -> {
            try {
                grpcServer = ServerBuilder.forPort(PORT)
                        .addService(mainHandler)
                        .build()
                        .start();
                log.info("Calendar gRPC Server started on port " + PORT);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    log.error("Shutting down gRPC server...");
                    if (grpcServer != null) grpcServer.shutdown();
                }));

                grpcServer.awaitTermination(); // keep server alive
            } catch (IOException | InterruptedException e) {
                log.info(e.getMessage());
            }
        }, "gRPC-Server-Thread");

        grpcThread.setDaemon(false); // non-daemon keeps JVM alive
        grpcThread.start();
    }
}
