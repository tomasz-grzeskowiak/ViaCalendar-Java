package via.sep3.viacalendar.services.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.repositories.database.EventRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        eventRepository.save(toUpdate);
    }
    @Transactional
    @Override
    public void delete(int id) {
        eventRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public EventProto getSingle(int id) {
        Optional<Event> fetched = eventRepository.findById(id);
        Event event = fetched.orElseThrow(() -> new RuntimeException("Event not found"));
        return EventProto.newBuilder()
                .setName(event.getName())
                .setId(event.getEventId())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public EventProtoList getMany() {
        List<Event> events = eventRepository.findAll();
        List<EventProto> eventProtos = events.stream()
                .map(event -> EventProto.newBuilder()
                        .setId(event.getEventId())
                        .setName(event.getName())
                        .build())
                .collect(Collectors.toList());
        return EventProtoList.newBuilder()
                .addAllEvents(eventProtos)
                .build();
    }
}
