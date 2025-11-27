package via.sep3.viacalendar.services.event;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.model.User;
import via.sep3.viacalendar.repositories.database.EventRepository;
import via.sep3.viacalendar.repositories.database.UserRepository;
import via.sep3.viacalendar.utils.ProtoUtilities;

import java.util.List;
import java.util.Optional;

@Service public class EventServiceDatabase implements EventService {
      private static final Logger log = LoggerFactory.getLogger(
          EventServiceDatabase.class);
      private final EventRepository eventRepository;
      private final UserRepository userRepository;
      
      public EventServiceDatabase(EventRepository eventRepository,
          UserRepository userRepository) {
            this.userRepository = userRepository;
            this.eventRepository = eventRepository;
      }
      
      @Transactional @Override public EventProto create(EventProto payload) {
            User creator = userRepository.findById(payload.getCreatorId())
                .orElseThrow();
            Event event = new Event(payload);
            event.setEventId(null);
            event.setCreator(creator);
            Event created = eventRepository.save(event);
            log.info("Event created: {}", event);
            return ProtoUtilities.parseEventToProto(created);
      }
      
      @Transactional @Override public void update(EventProto payload) {
            log.info("Updating event with id: {}", payload.getId());
            User creator = userRepository.findById(payload.getCreatorId())
                .orElseThrow();
            Event event = new Event(payload);
            event.setEventId(payload.getId());
            event.setCreator(creator);
            log.info("Event updated: {}", event);
            eventRepository.save(event);
      }
      
      @Transactional @Override public void delete(int id) {
            eventRepository.deleteById(id);
            log.info("Event deleted with id: {}", id);
      }
      
      @Transactional @Override public EventProto getSingle(int id) {
            Optional<Event> fetched = eventRepository.findById(id);
            Event event = fetched.orElseThrow(
                () -> new RuntimeException("Event not found"));
            log.info("Event found: {}", event);
            return ProtoUtilities.parseEventToProto(event);
            
      }
      
      @Transactional @Override public EventProtoList getMany() {
            List<Event> events = eventRepository.findAll();
            EventProtoList.Builder builder = EventProtoList.newBuilder();
            for (Event event : events) {
                  EventProto eventProto = ProtoUtilities.parseEventToProto(
                      event);
                  builder.addEvents(eventProto);
            }
            return builder.build();
      }
}
