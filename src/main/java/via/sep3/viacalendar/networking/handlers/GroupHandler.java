package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.GroupProto;
import via.sep3.viacalendar.services.group.GroupService;


 /**
 * Group Handler Service
 * <p>
 * Handles group-related actions such as creating, retrieving, updating, and deleting groups.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Service
public class GroupHandler implements ViaCalendarHandler {
   /** Group service for managing user groups */
  private final GroupService groupService;

  /**
   * Initializes a new instance of the GroupHandler class with the specified GroupService.
   *
   * @param groupService The service responsible for handling group operations.
   */
  public GroupHandler(GroupService groupService) {
            this.groupService = groupService;
      }

  /**
   * Handles an action based on the provided action type and payload.
   * <p>
   * Unpacks the payload into a GroupProto object, processes it according to the action type,
   * and returns the result as a packed Any message. If no proto is created, an empty GroupProto is returned.
   *
   * @param actionType The type of action to handle.
   * @param payload    The payload containing the data for the action.
   * @return A packed Any message representing the result of the action.
   * @throws RuntimeException if the payload cannot be unpacked or if an unknown action type is provided.
   */
  @Override
      public Message handle(ActionTypeProto actionType, Object payload) {
            Message proto = null;
            Any payloadAny = (Any) payload;
            GroupProto request;
            try
            {
                  request = payloadAny.unpack(GroupProto.class);
            }
            catch (InvalidProtocolBufferException e) {
                  throw new RuntimeException(e);
            }
            switch (actionType) {
                  case ACTION_CREATE -> proto = groupService.create(request);
                  case ACTION_GET -> proto = groupService.getSingle(request.getId());
                  case ACTION_UPDATE -> groupService.update(request);
                  case ACTION_DELETE -> groupService.delete(request.getId());
                  case ACTION_LIST -> proto = groupService.getMany();
                  default -> throw new RuntimeException("Unknown action type: " + actionType);
            }
            if (proto == null) {
                  proto = GroupProto.newBuilder().build();
            }
            
            return Any.pack(proto);
      }

  /**
   * Retrieves the handler type proto.
   *
   * @return The handler type proto.
   */
  public HandlerTypeProto getType() {
            return HandlerTypeProto.HANDLER_GROUP;
      }
}
