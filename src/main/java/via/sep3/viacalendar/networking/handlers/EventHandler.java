package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.services.event.EventService;

public class EventHandler implements ViaCalendarHandler {
    private final EventService eventService;
    public EventHandler(EventService eventService) {
        this.eventService = eventService;
    }
    @Override
    public Message handle(ActionTypeProto actionType, Object payload) {
        Message proto = null;
        Any payloadAny = (Any) payload;
        EventProto request;
        try
        {
            request = payloadAny.unpack(EventProto.class);
        } 
        catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        switch (actionType) {
            case ACTION_CREATE -> proto = eventService.create(request);
            case ACTION_GET -> proto = eventService.getSingle(request.getId());
            case ACTION_UPDATE -> eventService.update(request);
            case ACTION_DELETE -> eventService.delete(request.getId());
            case ACTION_LIST -> proto = eventService.getMany();
            default -> throw new RuntimeException("Unknown action type: " + actionType);
        }
        if (proto == null) {
            proto = EventProto.newBuilder().build();
        }
        return Any.pack(proto);
    }
}
