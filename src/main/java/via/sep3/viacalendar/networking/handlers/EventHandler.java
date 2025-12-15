package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.services.event.EventService;

/**
 * EventHandler Class
 * <p>
 * Implements the ViaCalendarHandler interface to handle different types of actions related to events.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Service
public class EventHandler implements ViaCalendarHandler {
   /** Event service for handling application events */
  private final EventService eventService;
  /**
   * Initializes a new instance of the EventHandler class with the specified EventService.
   *
   * @param eventService The service responsible for handling events.
   */
  public EventHandler(EventService eventService) {
        this.eventService = eventService;
    }
  /**
   * Handles an action based on the provided action type and payload.
   * <p>
   * Unpacks the payload into an EventProto object, processes it according to the action type,
   * and returns a packed Message.
   *
   * @param actionType The type of action to handle.
   * @param payload    The payload containing the data for the action.
   * @return A packed Message representing the result of the action.
   * @throws RuntimeException If the payload cannot be unpacked or an unknown action type is provided.
   */
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
  /**
   * Retrieves the handler type proto.
   *
   * @return The handler type proto.
   */
  public HandlerTypeProto getType() {
        return HandlerTypeProto.HANDLER_EVENT;
    }
}
