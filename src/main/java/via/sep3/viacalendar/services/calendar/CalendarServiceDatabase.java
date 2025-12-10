package via.sep3.viacalendar.services.calendar;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProtoList;
import via.sep3.viacalendar.model.Calendar;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.model.User;
import via.sep3.viacalendar.repositories.database.CalendarRepository;
import via.sep3.viacalendar.repositories.database.EventRepository;
import via.sep3.viacalendar.repositories.database.UserRepository;
import via.sep3.viacalendar.utils.ProtoUtilities;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CalendarServiceDatabase implements CalendarService {

    private static final Logger log = LoggerFactory.getLogger(
            CalendarServiceDatabase.class);

    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    public CalendarServiceDatabase(
            CalendarRepository calendarRepository,
            UserRepository userRepository,
            EventRepository eventRepository
    ) {
        this.calendarRepository = calendarRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    @Transactional
    @Override
    public CalendarProto create(CalendarProto payload) {
        System.out.println("=== DEBUG CREATE CALENDAR ===");
        System.out.println("User ID from payload: " + payload.getUserId());
        System.out.println("Event IDs from payload: " + payload.getEventListIdList());

        User user = userRepository.findById(payload.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + payload.getUserId()));

        // DON'T use Calendar(payload) constructor
        Calendar calendar = new Calendar(); // Use default constructor
        calendar.setId(null);
        calendar.setUser(user);

        if (payload.getEventListIdCount() > 0) {
            List<Integer> eventIds = payload.getEventListIdList().stream()
                    .mapToInt(Integer::intValue)
                    .boxed()
                    .toList();

            System.out.println("Looking for events with IDs: " + eventIds);

            List<Event> eventList = eventRepository.findAllById(eventIds);
            System.out.println("Found " + eventList.size() + " events in database");

            Set<Event> events = new HashSet<>(eventList);
            System.out.println("Setting " + events.size() + " events on calendar");
            calendar.setEvents(events);

            // CRITICAL: Update the bidirectional relationship!
            for (Event event : events) {
                event.getCalendars().add(calendar);
                System.out.println("Added calendar to event: " + event.getEventId());
            }
        } else {
            System.out.println("No event IDs provided in payload");
        }

        Calendar created = calendarRepository.save(calendar);
        System.out.println("=== CALENDAR SAVED ===");
        System.out.println("Calendar ID: " + created.getId());
        System.out.println("Calendar events count: " +
                (created.getEvents() != null ? created.getEvents().size() : 0));

        return ProtoUtilities.parseCalendarToProto(created);
    }

    @Transactional
    @Override
    public void update(CalendarProto payload) {
        System.out.println("=== DEBUG UPDATE CALENDAR ===");
        System.out.println("Updating calendar ID: " + payload.getId());
        System.out.println("New user ID: " + payload.getUserId());
        System.out.println("New event IDs: " + payload.getEventListIdList());

        // 1. Get existing calendar from database
        Calendar calendar = calendarRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Calendar not found: " + payload.getId()));

        // 2. Update user if changed
        if (!calendar.getUser().getId().equals(payload.getUserId())) {
            User user = userRepository.findById(payload.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found: " + payload.getUserId()));
            calendar.setUser(user);
        }

        // 3. Handle events - IMPORTANT: Clear existing events first
        System.out.println("Clearing existing events from calendar");

        // Remove this calendar from all current events
        if (calendar.getEvents() != null) {
            for (Event event : calendar.getEvents()) {
                event.getCalendars().remove(calendar);
            }
            calendar.getEvents().clear();
        }

        // 4. Add new events
        if (payload.getEventListIdCount() > 0) {
            List<Integer> eventIds = payload.getEventListIdList().stream()
                    .mapToInt(Integer::intValue)
                    .boxed()
                    .toList();

            System.out.println("Looking for events with IDs: " + eventIds);

            List<Event> eventList = eventRepository.findAllById(eventIds);
            System.out.println("Found " + eventList.size() + " events");

            Set<Event> events = new HashSet<>(eventList);
            System.out.println("Setting " + events.size() + " events on calendar");
            calendar.setEvents(events);

            // Add this calendar to each event
            for (Event event : events) {
                event.getCalendars().add(calendar);
                System.out.println("Added calendar to event: " + event.getEventId());
            }
        }

        System.out.println("Saving calendar...");
        calendarRepository.save(calendar);
        System.out.println("=== CALENDAR UPDATED ===");
    }

    @Transactional
    @Override
    public void delete(int id) {
        calendarRepository.deleteById(id);
        log.info("Calendar deleted with id: {}", id);
    }

    @Transactional
    @Override
    public CalendarProto getSingle(int id) {
        Optional<Calendar> fetched = calendarRepository.findById(id);
        Calendar calendar = fetched.orElseThrow(
                () -> new RuntimeException("Calendar not found"));

        log.info("Calendar found: {}", calendar);
        return ProtoUtilities.parseCalendarToProto(calendar);
    }

    @Transactional
    @Override
    public CalendarProtoList getMany() {
        List<Calendar> calendars = calendarRepository.findAll();
        CalendarProtoList.Builder builder = CalendarProtoList.newBuilder();

        for (Calendar calendar : calendars) {
            CalendarProto calendarProto = ProtoUtilities.parseCalendarToProto(
                    calendar);
            builder.addCalendars(calendarProto);
        }

        return builder.build();
    }
}