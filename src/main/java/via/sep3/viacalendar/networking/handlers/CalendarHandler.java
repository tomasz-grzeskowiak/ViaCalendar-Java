package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.services.calendar.CalendarService;

/**
 * Calendar Handler Service
 * <p>
 * Implements the ViaCalendarHandler interface to handle calendar-related actions such as creating, retrieving, updating, and deleting events.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Service
public class CalendarHandler implements ViaCalendarHandler {
  /**
   ```java
   /**
   * Calendar service instance
   */
  private final CalendarService calendarService;

  /**
   * Initializes a new instance of the CalendarHandler class with the specified CalendarService.
   *
   * @param calendarService The service responsible for handling calendar operations.
   */
  public CalendarHandler(CalendarService calendarService) {
        this.calendarService = calendarService;
    }
  /**
   * Handles an action based on the provided action type and payload.
   * <p>
   * Unpacks the payload into a CalendarProto object and processes it according to the action type.
   *
   * @param actionType The type of action to be handled.
   * @param payload    The payload containing the data for the action.
   * @return A packed Any message representing the result of the action.
   * @throws RuntimeException If an invalid protocol buffer exception occurs during unpacking or if an unknown action type is provided.
   */
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
  /**
   * Retrieves the type of handler as a Protocol Buffers enum value.
   *
   * @return The handler type, specifically HANDLER_CALENDAR.
   */
  @Override
    public HandlerTypeProto getType() {
        return HandlerTypeProto.HANDLER_CALENDAR;
    }
}