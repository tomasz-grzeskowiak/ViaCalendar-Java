package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.UserProto;
import via.sep3.viacalendar.services.user.UserService;

@Service
public class UserHandler implements ViaCalendarHandler{
    private final UserService userService;
    public UserHandler(UserService userService) {
        this.userService = userService;
    }
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
    public HandlerTypeProto getType() {
        return HandlerTypeProto.HANDLER_USER;
    }
}
