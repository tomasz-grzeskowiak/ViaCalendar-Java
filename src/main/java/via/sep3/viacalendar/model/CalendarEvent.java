package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * The type Calendar event.
 */
@Entity
@Table(name = "calendar_events", schema = "via_calendar")
public class CalendarEvent {
    @EmbeddedId
    private CalendarEventId id;

    @MapsId("calendarId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "calendar_id", nullable = false)
    private Calendar calendar;

    @MapsId("eventId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

  /**
   * Gets id.
   *
   * @return the id
   */
  public CalendarEventId getId() {
        return id;
    }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(CalendarEventId id) {
        this.id = id;
    }

  /**
   * Gets calendar.
   *
   * @return the calendar
   */
  public Calendar getCalendar() {
        return calendar;
    }

  /**
   * Sets calendar.
   *
   * @param calendar the calendar
   */
  public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

  /**
   * Gets event.
   *
   * @return the event
   */
  public Event getEvent() {
        return event;
    }

  /**
   * Sets event.
   *
   * @param event the event
   */
  public void setEvent(Event event) {
        this.event = event;
    }

}