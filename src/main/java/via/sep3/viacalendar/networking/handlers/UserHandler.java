package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.UserProto;
import via.sep3.viacalendar.services.user.UserService;

/**
 * User Handler Service
 * <p>
 * Handles user-related actions such as creating, retrieving, updating, and deleting users.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Service
public class UserHandler implements ViaCalendarHandler{
  /** User service instance */
  private final UserService userService;

  /**
   * Initializes a new instance of the UserHandler class with the specified UserService.
   *
   * @param userService The service responsible for handling user operations.
   */
  public UserHandler(UserService userService) {
        this.userService = userService;
    }

  /**
   * Handles an action based on the provided action type and payload.
   * <p>
   * Unpacks the payload into a UserProto object, processes it according to the action type,
   * and returns the result as a packed Any message. If no proto is created, returns a default UserProto.
   *
   * @param actionType The type of action to handle.
   * @param payload    The payload containing the data for the action.
   * @return A packed Any message representing the result of the action.
   * @throws RuntimeException if an error occurs during unpacking or processing.
   */
  @Override
    public Message handle(ActionTypeProto actionType, Object payload) {
        Message proto = null;
        Any payloadAny = (Any) payload;
        UserProto request;
        try
        {
            request = payloadAny.unpack(UserProto.class);
        }
        catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
        switch (actionType) {
            case ACTION_CREATE -> proto = userService.create(request);
            case ACTION_GET -> proto = userService.getSingle(request.getId());
            case ACTION_UPDATE -> userService.update(request);
            case ACTION_DELETE -> userService.delete(request.getId());
            case ACTION_LIST -> proto = userService.getMany();
            default -> throw new RuntimeException("Unknown action type: " + actionType);
        }
        if (proto == null) {
            proto = UserProto.newBuilder().build();
        }

        return Any.pack(proto);
    }

  /**
   * Retrieves the handler type as a proto enum value.
   *
   * @return The handler type proto enum value.
   */
  public HandlerTypeProto getType() {
        return HandlerTypeProto.HANDLER_USER;
    }
}
