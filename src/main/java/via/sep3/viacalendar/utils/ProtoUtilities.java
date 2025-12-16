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

/**
 * Utility Class for Parsing Objects to Protobuf
 * <p>
 * Provides methods to convert domain objects (User, Group, Calendar, Event) into their corresponding Protobuf representations.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
public class ProtoUtilities {
  /**
   * Parses a User object into a UserProto.
   * <p>
   * Converts the given User object into a UserProto, handling null values appropriately.
   *
   * @param user The User object to be parsed.
   * @return A UserProto representing the User object.
   * @throws RuntimeException If the input User is null.
   */
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

  /**
   * Parses a Group object into a GroupProto.
   * <p>
   * Converts the provided Group object into its corresponding protobuf representation. If the input Group is null, it throws a RuntimeException.
   *
   * @param group The Group object to be parsed.
   * @return A GroupProto representing the input Group.
   * @throws RuntimeException if the input Group is null.
   */
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

  /**
   * Parses a Calendar object into a CalendarProto.
   * <p>
   * Converts the given Calendar object into its corresponding CalendarProto representation.
   *
   * @param calendar The Calendar object to be parsed.
   * @return A CalendarProto representing the input Calendar.
   * @throws RuntimeException If the input Calendar is null.
   */
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

  /**
   * Parses an Event object into an EventProto.
   * <p>
   * Converts the given Event object into its corresponding EventProto representation,
   * handling null values appropriately and throwing a RuntimeException if the input Event is null.
   *
   * @param event The Event object to be parsed.
   * @return The EventProto representation of the input Event.
   * @throws RuntimeException Thrown when the input Event is null.
   */
  public static EventProto parseEventToProto(Event event) {
      EventProto.Builder builder = EventProto.newBuilder();

      if(event.getEventId() == null) {
          throw new RuntimeException("Event is null");
      }

      builder.setId(event.getEventId())
              .setName(event.getName() == null ? "" : event.getName())
              .setTag(event.getTag() == null ? "" : event.getTag())
              .setRecursive(event.getRecursive() != null && event.getRecursive())
              .setStart(event.getStart() == null ?
                      Timestamp.getDefaultInstance() :
                      Timestamp.newBuilder()
                              .setSeconds(event.getStart().getEpochSecond())
                              .build())
              .setEndTime(event.getEnd() == null ?
                      Timestamp.getDefaultInstance() :
                      Timestamp.newBuilder()
                              .setSeconds(event.getEnd().getEpochSecond())
                              .build())
              .setTypeOfRecursive(parseStringToTypeOfRecursive(event.getTypeOfRecursive()))
              .setCreatorId(event.getCreator().getId() == null ?
                      0 : event.getCreator().getId());

      return builder.build();
  }

  /**
   * Parses a string to the corresponding TypeOfRecursiveProto enum value.
   * <p>
   * Converts a given string message into its corresponding enum value from TypeOfRecursiveProto.
   * If the input string does not match any known values, returns TypeOfRecursiveProto.NONE.
   *
   * @param message The string message to parse.
   * @return The corresponding TypeOfRecursiveProto enum value.
   */
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

  /**
   * Parses the type of recursive proto to a string representation.
   * <p>
   * Converts the given TypeOfRecursiveProto enum value to its corresponding string representation.
   *
   * @param proto The TypeOfRecursiveProto enum value to parse.
   * @return The string representation of the proto, or null if the input is null.
   */
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
