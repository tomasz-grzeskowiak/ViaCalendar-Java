package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.GroupProto;
import via.sep3.viacalendar.services.group.GroupService;

@Service
public class GroupHandler implements ViaCalendarHandler {
      private final GroupService groupService;
      public GroupHandler(GroupService groupService) {
            this.groupService = groupService;
      }
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
      public HandlerTypeProto getType() {
            return HandlerTypeProto.HANDLER_GROUP;
      }
}
