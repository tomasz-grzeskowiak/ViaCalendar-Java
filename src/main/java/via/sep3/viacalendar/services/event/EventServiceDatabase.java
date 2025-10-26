package via.sep3.viacalendar.services.event;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.repositories.database.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceDatabase implements EventService {
    private final EventRepository eventRepository;
    public EventServiceDatabase(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }
    @Transactional
    @Override
    public EventProto create(EventProto payload) {
        Event event = new Event();
        event.setName(payload.getName());
        Event created = eventRepository.save(event);
        return EventProto.newBuilder()
                .setId(created.getEventId())
                .setName(created.getName())
                .build();
    }
    @Transactional
    @Override
    public void update(EventProto payload) {
        Event toUpdate = eventRepository.findById(payload.getId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        toUpdate.setName(payload.getName());
        Event updated = eventRepository.save(toUpdate);
    }
    @Transactional
    @Override
    public void delete(int id) {
        eventRepository.deleteById(id);
    }

    @Override
    public EventProto getSingle(int id) {
        Optional<Event> fetched = eventRepository.findById(id);
        Event event = fetched.orElseThrow(() -> new RuntimeException("Event not found"));
        return EventProto.newBuilder()
                .setName(event.getName())
                .setId(event.getEventId())
                .build();
    }

    @Override
    public EventProtoList getMany() {
        List<Event> events = eventRepository.findAll();
        EventProtoList.Builder builder = EventProtoList.newBuilder();
        for (Event event : events) {
            EventProto eventProto = EventProto.newBuilder()
                    .setId(event.getEventId())
                    .setName(event.getName())
                    .build();
            builder.addEvents(eventProto);
        }
        return builder.build();
    }
}
