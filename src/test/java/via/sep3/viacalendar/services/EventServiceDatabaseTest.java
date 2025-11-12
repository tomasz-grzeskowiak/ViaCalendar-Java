package via.sep3.viacalendar.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;
import via.sep3.viacalendar.model.Event;
import via.sep3.viacalendar.repositories.database.EventRepository;
import via.sep3.viacalendar.services.event.EventServiceDatabase;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EventServiceDatabaseTest {

    @Mock
    private EventRepository eventRepository;

    private EventServiceDatabase eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventServiceDatabase(eventRepository);
    }

    @Test
    void testGetMany_ReturnsMultipleEvents() {
        // Arrange
        Event event1 = new Event();
        event1.setEventId(1);
        event1.setName("Event 1");

        Event event2 = new Event();
        event2.setEventId(2);
        event2.setName("Event 2");

        List<Event> events = Arrays.asList(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        // Act
        EventProtoList result = eventService.getMany();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getEventsCount());
        assertEquals("Event 1", result.getEvents(0).getName());
        assertEquals(1, result.getEvents(0).getId());
        assertEquals("Event 2", result.getEvents(1).getName());
        assertEquals(2, result.getEvents(1).getId());
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void testGetSingle_ReturnsEvent() {
        // Arrange
        Event event = new Event();
        event.setEventId(1);
        event.setName("Test Event");
        when(eventRepository.findById(1)).thenReturn(Optional.of(event));

        // Act
        EventProto result = eventService.getSingle(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Test Event", result.getName());
        verify(eventRepository, times(1)).findById(1);
    }

    @Test
    void testCreate_SavesAndReturnsEvent() {
        // Arrange
        EventProto inputProto = EventProto.newBuilder()
                .setName("New Event")
                .build();

        Event savedEvent = new Event();
        savedEvent.setEventId(1);
        savedEvent.setName("New Event");

        when(eventRepository.save(any(Event.class))).thenReturn(savedEvent);

        // Act
        EventProto result = eventService.create(inputProto);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("New Event", result.getName());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    void testUpdate_UpdatesEvent() {
        // Arrange
        EventProto updateProto = EventProto.newBuilder()
                .setId(1)
                .setName("Updated Event")
                .build();

        Event existingEvent = new Event();
        existingEvent.setEventId(1);
        existingEvent.setName("Old Event");

        when(eventRepository.findById(1)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(any(Event.class))).thenReturn(existingEvent);

        // Act
        eventService.update(updateProto);

        // Assert
        verify(eventRepository, times(1)).findById(1);
        verify(eventRepository, times(1)).save(existingEvent);
        assertEquals("Updated Event", existingEvent.getName());
    }

    @Test
    void testDelete_DeletesEvent() {
        // Arrange
        int eventId = 1;

        // Act
        eventService.delete(eventId);

        // Assert
        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void testGetSingle_ThrowsExceptionWhenNotFound() {
        // Arrange
        when(eventRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> eventService.getSingle(999));
        verify(eventRepository, times(1)).findById(999);
    }

    @Test
    void testUpdate_ThrowsExceptionWhenNotFound() {
        // Arrange
        EventProto updateProto = EventProto.newBuilder()
                .setId(999)
                .setName("Updated Event")
                .build();

        when(eventRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> eventService.update(updateProto));
        verify(eventRepository, times(1)).findById(999);
        verify(eventRepository, never()).save(any(Event.class));
    }
}
