package via.sep3.viacalendar.utils;
import via.sep3.viacalendar.model.*;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.GroupProto;
import via.sep3.viacalendar.gRPC.Calendar.UserProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;
import via.sep3.viacalendar.gRPC.Calendar.UserProtoList;

import java.util.stream.Collectors;

public class ProtoUtilities {
    public static UserProto parseUserToProto(User user)
    {

        return UserProto.newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setGroup(parseGroupToProto(user.getGroup()))
                .build();

    }
    public static GroupProto parseGroupToProto(Group group)
    {
        return GroupProto.newBuilder()
                .setId(group.getId())
                .setName(group.getName())
                .addAllUsers(
                        group.getUsers()
                                .stream()
                                .map(User::getId)
                                .collect(Collectors.toList()))
                .build();
    }
    public static CalendarProto parseCalendarToProto(Calendar calendar)
    {
        return CalendarProto.newBuilder()
                .build();
    }
    public static EventProto parseEventToProto(Event event)
    {
        return EventProto.newBuilder()
                .setId(event.getId())
                .build();
    }
}
