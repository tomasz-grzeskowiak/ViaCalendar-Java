package via.sep3.viacalendar.networking;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Via Server Component
 * <p>
 * Manages the lifecycle of a gRPC server for handling calendar-related requests.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Component
public class ViaServer {
   /**
   * Application logger for ViaServer class
   */
  private static final Logger log = LoggerFactory.getLogger(ViaServer.class);
  /** Port number for the server */
  private static final int PORT = 6032;
  /** Main calendar handler for managing calendar operations */
  private final ViaCalendarMainHandler mainHandler;
  /** gRPC server instance */
  private Server grpcServer;

  /**
   * Initializes a new instance of the ViaServer class with the specified main handler.
   *
   * @param mainHandler The main handler for handling calendar-related operations.
   */
  public ViaServer(ViaCalendarMainHandler mainHandler) {
        this.mainHandler = mainHandler;
    }

  /**
   * Starts the gRPC server.
   * <p>
   * Initializes a new thread to run the gRPC server, binds it to a specified port,
   * and sets up a shutdown hook to gracefully shut down the server when the JVM exits.
   *
   * @throws IOException          If an I/O error occurs during server startup.
   * @throws InterruptedException If the current thread is interrupted while waiting for the server to terminate.
   */
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
