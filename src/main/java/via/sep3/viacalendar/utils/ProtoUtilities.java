package via.sep3.viacalendar.utils;
import via.sep3.viacalendar.model.*;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.GroupProto;
import via.sep3.viacalendar.gRPC.Calendar.UserProto;
import com.google.protobuf.Timestamp;
import via.sep3.viacalendar.gRPC.Calendar.TypeOfRecursiveProto;

import java.util.LinkedHashSet;
import java.util.stream.Collectors;

public class ProtoUtilities {
    public static UserProto parseUserToProto(User user)
    {
        if(user == null)
        {
            throw new RuntimeException("User is null");
        }
        return UserProto.newBuilder()
                .setId(user.getId())
                .setEmail(user.getEmail() == null ? "default@default.com" : user.getEmail())
                .setUsername(user.getUsername() == null ? "default" : user.getUsername())
                .setPassword(user.getPassword() == null ? "DefaultSecure123!" : user.getPassword())
                .setFirstName(user.getFirstName() == null ? "Default" : user.getFirstName())
                .setLastName(user.getLastName() == null ? "Default" : user.getLastName())
                .setGroupId(user.getGroup() == null ? 0 : user.getGroup().getId())
                .addAllEventIds(
                        user.getEvents() == null ?
                                new LinkedHashSet<>() :
                                user.getEvents().
                                        stream().
                                        map(Event::getEventId)
                                        .toList())
                .addAllCalendarsIds(user.getCalendars() == null ?
                        new LinkedHashSet<>() :
                        user.getCalendars().
                                stream().
                                map(Calendar::getId)
                                .toList())
                .build();
    }
    public static GroupProto parseGroupToProto(Group group)
    {
        if(group == null)
        {
            throw new RuntimeException("Group is null");
        }
        return GroupProto.newBuilder()
                .setId(group.getId() == null ? 0 : group.getId())
                .setName(group.getName() == null ? "Default" : group.getName())
                .addAllUsers(
                        group.getUsers()
                                .stream()
                                .map(User::getId)
                                .collect(Collectors.toList()))
                .build();
    }
    public static CalendarProto parseCalendarToProto(Calendar calendar)
    {
        if(calendar == null)
        {
            throw new RuntimeException("Calendar is null");
        }
        return CalendarProto.newBuilder()
                .addAllEventListId(
                        calendar.getEvents()
                                .stream()
                                .map(Event::getEventId)
                                .toList()
                )
                .setId(calendar.getId())
                .setUserId(calendar.getUser().getId())
                .build();
    }
    public static EventProto parseEventToProto(Event event) {
        EventProto.Builder builder = EventProto.newBuilder();
        if(event.getEventId() == null)
        {
            throw new RuntimeException("Event is null");
        }
        builder.setId(event.getEventId())
                .setName(event.getName() == null ? "" : event.getName() )
                .setTag(event.getTag() == null ? "" : event.getTag())
                .setRecursive(event.getRecursive() != null && event.getRecursive())
                .setDuration(event.getDuration() == null ?
                        Timestamp.getDefaultInstance() :
                        Timestamp.newBuilder().setSeconds
                                (event.getDuration().getEpochSecond())
                                .build())
                .setTypeOfRecursive(parseStringToTypeOfRecursive(event.getTypeOfRecursive()))
                .setCreatorId(event.getCreator().getId() == null ?
                        0 : event.getCreator().getId());
            return builder.build();
    }
    public static TypeOfRecursiveProto parseStringToTypeOfRecursive(String message)
    {
        if(message == null)
        {
            return TypeOfRecursiveProto.NONE;
        }
        switch (message) {
            case "Day" -> {
                return TypeOfRecursiveProto.DAY;
            }
            case "Month" -> {
                return TypeOfRecursiveProto.MONTH;
            }
            case "Year" -> {
                return TypeOfRecursiveProto.YEAR;
            }
            default -> {
                return TypeOfRecursiveProto.NONE;
            }
        }
    }
    public static String parseTypeOfRecursiveToString(TypeOfRecursiveProto proto)
    {
        String result = null;
        switch (proto) {
            case DAY -> result = "Day";
            case MONTH -> result = "Month";
            case YEAR -> result = "Year";
            case null ,default -> {
                return result;
            }
        }
        return result;
    }
}
