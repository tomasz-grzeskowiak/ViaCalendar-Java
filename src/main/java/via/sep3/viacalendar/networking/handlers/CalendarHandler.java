package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.services.calendar.CalendarService;

@Service
public class CalendarHandler implements ViaCalendarHandler {
    private final CalendarService calendarService;

    public CalendarHandler(CalendarService calendarService) {
        this.calendarService = calendarService;
    }

    @Override
    public Message handle(ActionTypeProto actionType, Object payload) {
        Message proto = null;
        Any payloadAny = (Any) payload;
        CalendarProto request;

        try {
            request = payloadAny.unpack(CalendarProto.class);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }

        switch (actionType) {
            case ACTION_CREATE -> proto = calendarService.create(request);
            case ACTION_GET -> proto = calendarService.getSingle(request.getId());
            case ACTION_UPDATE -> calendarService.update(request);
            case ACTION_DELETE -> calendarService.delete(request.getId());
            case ACTION_LIST -> proto = calendarService.getMany();
            default -> throw new RuntimeException("Unknown action type: " + actionType);
        }

        if (proto == null) {
            proto = CalendarProto.newBuilder().build();
        }

        return Any.pack(proto);
    }

    @Override
    public HandlerTypeProto getType() {
        return HandlerTypeProto.HANDLER_CALENDAR;
    }
}