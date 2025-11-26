package via.sep3.viacalendar.services.user;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.model.User;
import via.sep3.viacalendar.repositories.database.CalendarRepository;
import via.sep3.viacalendar.repositories.database.EventRepository;
import via.sep3.viacalendar.repositories.database.UserRepository;
import via.sep3.viacalendar.services.event.EventService;
import via.sep3.viacalendar.utils.ProtoUtilities;

import java.util.List;
import java.util.Optional;


@Service
public class UserServiceDatabase implements UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceDatabase.class);
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CalendarRepository calendarRepository;
    public UserServiceDatabase(UserRepository userRepository,
                               EventRepository eventRepository,
                               CalendarRepository calendarRepository){
        //Add group repository
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.calendarRepository = calendarRepository;
    }
    @Transactional
    @Override
    public Calendar.UserProto create(Calendar.UserProto payload) {
        User user = new User(payload);
        user.setId(null);//to create a new user
        user.setCalendars(calendarRepository.findAllById(payload.getCalendarsIdsList()));
        user.setEvents(eventRepository.findAllById(payload.getEventIdsList()));
        User created = userRepository.save(user);
        log.info("User created: {}", user);
        return ProtoUtilities.parseUserToProto(created);
    }

    @Transactional
    @Override
    public void update(Calendar.UserProto payload) {
        User user = new User(payload);
        user.setCalendars(calendarRepository.findAllById(payload.getCalendarsIdsList()));
        user.setEvents(eventRepository.findAllById(payload.getEventIdsList()));
        userRepository.save(user);
        log.info("User updated: {}", user);
    }

    @Transactional
    @Override
    public void delete(int id) {
        userRepository.deleteById(id);
        log.info("User deleted with id: {}", id);
    }
    @Transactional
    @Override
    public Calendar.UserProto getSingle(int id) {
        Optional<User> fetched = userRepository.findById(id);
        User user = fetched.orElseThrow(() -> new RuntimeException("Event not found"));
        log.info("User found: {}", user);
        return ProtoUtilities.parseUserToProto(user);

    }
    @Transactional
    @Override
    public Calendar.UserProtoList getMany() {
        List<User> users = userRepository.findAll();
        Calendar.UserProtoList.Builder builder = Calendar.UserProtoList.newBuilder();
        for (User user : users) {
            Calendar.UserProto userProto = ProtoUtilities.parseUserToProto(user);
            builder.addUsers(userProto);
        }
        return builder.build();
    }
}
