package via.sep3.viacalendar.networking;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.google.protobuf.StringValue;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.StatusTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.RequestProto;
import via.sep3.viacalendar.gRPC.Calendar.ResponseProto;
import via.sep3.viacalendar.gRPC.CalendarProtoServiceGrpc;
import via.sep3.viacalendar.networking.handlers.ViaCalendarHandler;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;

import java.util.Map;

@Service
@GRpcService
public class ViaCalendarMainHandler extends CalendarProtoServiceGrpc.CalendarProtoServiceImplBase {
    private final static Logger log =  LoggerFactory.getLogger(ViaCalendarMainHandler.class);
    private final Map<HandlerTypeProto, ViaCalendarHandler> serviceProvider;

    public ViaCalendarMainHandler(Map<HandlerTypeProto, ViaCalendarHandler> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }
    @Override
    public void sendRequest(RequestProto request, StreamObserver<ResponseProto> responseObserver) {
        try {
            // Route request based on HandlerType
            ViaCalendarHandler handler = serviceProvider.get(request.getHandler());
            if (handler == null) {
                log.error("Unknown handler type for request {}", request);
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
            log.info("Sending response {}", response);
            sendResponseWithHandleException(responseObserver, response);

        } catch (Exception e) {
            sendGrpcError(responseObserver, StatusTypeProto.STATUS_ERROR, e.getMessage());
        }
    }
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
            log.error("Error sending response", e);
        }
    }
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
