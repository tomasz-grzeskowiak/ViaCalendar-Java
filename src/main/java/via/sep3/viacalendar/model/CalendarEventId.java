package via.sep3.viacalendar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

/**
 * Calendar Event ID Embeddable Class
 * <p>
 * Represents a composite primary key for the CalendarEvent entity, consisting of calendarId and eventId.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Embeddable
public class CalendarEventId implements Serializable {
   /**
   * Unique identifier for serialization purposes
   */
  private static final long serialVersionUID = 8076233605126528611L;
   /**
   * Calendar ID
   * <p>
   * Unique identifier for the calendar.
   *
   * @see Column
   */
  @Column(name = "calendar_id", nullable = false)
    private Integer calendarId;

   /**
   * Event ID
   * <p>
   * Unique identifier for the event.
   *
   * @see Column
   */
  @Column(name = "event_id", nullable = false)
    private Integer eventId;

  /**
   * Retrieves the calendar ID.
   * <p>
   * Returns the current calendar ID.
   *
   * @return The calendar ID.
   */
  public Integer getCalendarId() {
        return calendarId;
    }

  /**
   * Sets the calendar ID for the current instance.
   *
   * @param calendarId The ID of the calendar to be set.
   */
  public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

  /**
   * Retrieves the event ID.
   * <p>
   * Returns the current event ID.
   *
   * @return The event ID.
   */
  public Integer getEventId() {
        return eventId;
    }

  /**
   * Sets the event ID for the current instance.
   *
   * @param eventId The ID of the event to be set.
   */
  public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

  /**
   * Compares this object with the specified object for equality.
   * <p>
   * Two objects are considered equal if they have the same event ID and calendar ID.
   *
   * @param o The object to compare with.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CalendarEventId entity = (CalendarEventId) o;
        return Objects.equals(this.eventId, entity.eventId) &&
                Objects.equals(this.calendarId, entity.calendarId);
    }

  /**
   * Generates a hash code for this object based on its event ID and calendar ID.
   *
   * @return A hash code value for this object.
   */
  @Override
    public int hashCode() {
        return Objects.hash(eventId, calendarId);
    }

}