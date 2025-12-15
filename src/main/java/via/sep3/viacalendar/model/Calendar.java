package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Calendar Entity Class
 * <p>
 * Represents a calendar entity in the database, which includes relationships with users and events.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Entity
@Table(name = "calendars", schema = "via_calendar")
public class Calendar {
   /**
   * Calendar ID
   * <p>
   * Unique identifier for the calendar.
   *
   * @see java.lang.Integer
   */
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id", nullable = false)
    private Integer id;
  /**
   * The associated user for this entity.
   * <p>
   * This field represents the user who is linked to the current entity. It is a many-to-one relationship where each entity can be associated with one user, but a user can have multiple entities.
   *
   * @see User
   */
  @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;
   /**
   * List of events associated with this calendar.
   * <p>
   * Contains a set of events that are linked to this calendar. The set is ordered using a {@link LinkedHashSet} to maintain insertion order.
   *
   * @see Event
   */
  @ManyToMany(mappedBy = "calendars")
    private Set<Event> events = new LinkedHashSet<>();
  /**
   * Constructs a new instance of the Calendar class.
   * <p>
   * Initializes a new calendar object with default settings.
   */
  public Calendar(){}; //default constructor
  /**
   * Constructs a new Calendar instance from a CalendarProto object.
   * <p>
   * Initializes a new Calendar with the data provided in the CalendarProto.
   *
   * @param proto The CalendarProto object containing the calendar data.
   */
  public Calendar(CalendarProto proto){
    }
  /**
   * Retrieves a set of all events.
   * <p>
   * Returns the current list of events.
   *
   * @return A set containing all events.
   */
  public Set<Event> getEvents() {
        return events;
    }
  /**
   * Sets the events for the current object.
   *
   * @param events The set of events to be assigned.
   */
  public void setEvents(Set<Event> events) {
        this.events = events;
    }
  /**
   * Retrieves the ID of the object.
   * <p>
   * Returns the unique identifier for this object.
   *
   * @return The ID of the object.
   */
  public Integer getId() {
        return id;
    }
  /**
   * Sets the ID for the current object.
   * <p>
   * Updates the internal ID field with the provided value.
   *
   * @param id The new ID to set.
   */
  public void setId(Integer id) {
        this.id = id;
    }
  /**
   * Retrieves the current user object.
   * <p>
   * Returns the user object that is currently being managed by this instance.
   *
   * @return The current user object.
   */
  public User getUser() {
        return user;
    }
  /**
   * Sets the user object.
   * <p>
   * Updates the internal user reference with the provided user object.
   *
   * @param user The user object to set.
   */
  public void setUser(User user) {
        this.user = user;
    }

}