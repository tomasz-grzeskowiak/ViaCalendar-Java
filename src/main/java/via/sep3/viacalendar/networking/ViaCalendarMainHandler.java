package via.sep3.viacalendar.networking;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.StatusTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.RequestProto;
import via.sep3.viacalendar.gRPC.Calendar.ResponseProto;
import via.sep3.viacalendar.gRPC.CalendarProtoServiceGrpc;
import via.sep3.viacalendar.networking.handlers.ViaCalendarHandler;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;

import java.util.Map;


 /**
 * ViaCalendarMainHandler Class
 * <p>
 * Handles incoming gRPC requests and routes them to the appropriate handler based on the HandlerType.
 * It processes the request, invokes the handler's action, and sends back a response.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Service
@GRpcService
public class ViaCalendarMainHandler extends CalendarProtoServiceGrpc.CalendarProtoServiceImplBase {
   /**
   * Service provider mapping for different handler types.
   * <p>
   * This map holds instances of {@link ViaCalendarHandler} associated with specific {@link HandlerTypeProto}.
   *
   * @see HandlerTypeProto
   * @see ViaCalendarHandler
   */
  private final Map<HandlerTypeProto, ViaCalendarHandler> serviceProvider;
   /**
   * Application logger for ViaCalendarMainHandler class.
   */
  private final Logger logger = LoggerFactory.getLogger(ViaCalendarMainHandler.class);

  /**
   * Initializes a new instance of the ViaCalendarMainHandler class with the specified service provider.
   *
   * @param serviceProvider A map containing handler types and their corresponding handlers.
   */
  public ViaCalendarMainHandler(Map<HandlerTypeProto, ViaCalendarHandler> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

  /**
   * Sends a request to the appropriate handler and processes the response.
   * <p>
   * This method routes the incoming request to the correct handler based on the HandlerType,
   * handles the action and payload, and sends back a response. If an error occurs during processing,
   * it logs the error and sends an error response.
   *
   * @param request          The protobuf request object containing the handler type, action, and payload.
   * @param responseObserver The observer to send the response back to the client.
   * @throws IllegalArgumentException Thrown if the handler type is unknown.
   */
  @Override
    public void sendRequest(RequestProto request, StreamObserver<ResponseProto> responseObserver) {
        try {
            // Route request based on HandlerType
            logger.info("Received request: " + request.toString());
            ViaCalendarHandler handler = serviceProvider.get(request.getHandler());
            if (handler == null) {
                logger.error("Unknown handler type: " + request.getHandler());
                throw new IllegalArgumentException("Unknown handler type");
            }
            // Message is the protobuf object
            Message result = handler.handle(request.getAction(), request.getPayload());
            // Only pack if not already an Any
            Any payload;
            if (result instanceof Any) {
                payload = (Any) result;
            } else {
                payload = Any.pack(result);
            }

            ResponseProto response = ResponseProto.newBuilder()
                    .setStatus(StatusTypeProto.STATUS_OK)
                    .setPayload(payload)
                    .build();
            sendResponseWithHandleException(responseObserver, response);

        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_ERROR, e.getMessage());
        }
    }

  /**
   * Sends a response to the client and handles exceptions.
   * <p>
   * This method sends a response using the provided StreamObserver. It also handles potential exceptions that may occur during the process, such as ClassCastException and other generic exceptions.
   *
   * @param responseObserver The observer used to send responses to the client.
   * @param response         The response to be sent to the client.
   */
  private void sendResponseWithHandleException(StreamObserver<ResponseProto> responseObserver, ResponseProto response)
    {
        try {
            responseObserver.onNext(response);
        } catch (ClassCastException e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_INVALID_PAYLOAD, "Invalid request");
            return;
        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_ERROR, e.getMessage());
            return;
        }
        try {
            responseObserver.onCompleted();
        } catch (Exception e) {
            System.err.println("Error completing gRPC response: " + e.getMessage());
        }
    }

  /**
   * Sends an gRPC error response.
   * <p>
   * Constructs a gRPC response with the given status and error message, then sends it to the provided observer.
   *
   * @param observer     The StreamObserver to send the response to.
   * @param status       The StatusTypeProto representing the error status.
   * @param errorMessage The error message to include in the response.
   */
  private void sendGrpcError(StreamObserver<ResponseProto> observer, StatusTypeProto status, String errorMessage) {
        Any payload =Any.pack(StringValue.of(errorMessage));// convert error message to protobuf message
        ResponseProto response = ResponseProto.newBuilder().
                setStatus(status).
                setPayload(payload)
                .build();
        observer.onNext(response);
        observer.onCompleted();
    }
}
