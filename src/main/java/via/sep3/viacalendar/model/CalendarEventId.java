package via.sep3.viacalendar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Calendar event id.
 */
@Embeddable
public class CalendarEventId implements Serializable {
    private static final long serialVersionUID = 8076233605126528611L;
    @Column(name = "calendar_id", nullable = false)
    private Integer calendarId;

    @Column(name = "event_id", nullable = false)
    private Integer eventId;

  /**
   * Gets calendar id.
   *
   * @return the calendar id
   */
  public Integer getCalendarId() {
        return calendarId;
    }

  /**
   * Sets calendar id.
   *
   * @param calendarId the calendar id
   */
  public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

  /**
   * Gets event id.
   *
   * @return the event id
   */
  public Integer getEventId() {
        return eventId;
    }

  /**
   * Sets event id.
   *
   * @param eventId the event id
   */
  public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CalendarEventId entity = (CalendarEventId) o;
        return Objects.equals(this.eventId, entity.eventId) &&
                Objects.equals(this.calendarId, entity.calendarId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, calendarId);
    }

}