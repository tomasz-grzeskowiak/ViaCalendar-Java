package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;



public interface ViaCalendarHandler {
    Message handle(ActionTypeProto actionType, Object payload);
    HandlerTypeProto getType();
}
