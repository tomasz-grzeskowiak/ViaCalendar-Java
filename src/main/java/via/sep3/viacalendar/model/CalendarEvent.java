package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * Calendar Event Entity Class
 * <p>
 * Represents a calendar event with a composite primary key consisting of calendar and event IDs.
 * This entity is used to manage events within specific calendars in the system.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Entity
@Table(name = "calendar_events", schema = "via_calendar")
public class CalendarEvent {
   /**
   * Event identifier for calendar events.
   */
  @EmbeddedId
    private CalendarEventId id;

   /**
   * Calendar associated with the entity.
   * <p>
   * This field represents the calendar that is linked to the current entity. It is a mandatory, non-nullable field that is lazily loaded and will be deleted if this entity is removed.
   *
   * @see Calendar
   */
  @MapsId("calendarId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

   /**
   * Event associated with the entity.
   * <p>
   * This field represents the event to which the entity is linked. It is a mandatory, non-nullable field that establishes a lazy-loaded relationship with the Event entity.
   *
   * @see Event
   */
  @MapsId("eventId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

  /**
   * Retrieves the unique identifier for this calendar event.
   *
   * @return The unique identifier for the calendar event.
   */
  public CalendarEventId getId() {
        return id;
    }

  /**
   * Sets the event ID for the calendar event.
   *
   * @param id The new ID for the calendar event.
   */
  public void setId(CalendarEventId id) {
        this.id = id;
    }

  /**
   * Retrieves the current calendar instance.
   * <p>
   * Returns the calendar object that represents the current date and time.
   *
   * @return The current calendar instance.
   */
  public Calendar getCalendar() {
        return calendar;
    }

  /**
   * Sets the calendar for this instance.
   * <p>
   * Updates the internal calendar reference with the provided calendar object.
   *
   * @param calendar The new calendar to be used.
   */
  public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

  /**
   * Retrieves the current event.
   * <p>
   * Returns the event object that is currently being processed.
   *
   * @return The current event.
   */
  public Event getEvent() {
        return event;
    }

  /**
   * Sets the event for this object.
   * <p>
   * Updates the internal state with the provided event.
   *
   * @param event The event to be set.
   */
  public void setEvent(Event event) {
        this.event = event;
    }

}