package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.utils.ProtoUtilities;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Event Entity Class
 * <p>
 * Represents an event in the calendar system, including details such as name, tag, recursion, creator, duration, and associated calendars.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */

@Entity
@Table (name = "events", schema = "via_calendar")
public class Event {
   /**
   * Event ID
   */
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer eventId;
   /**
   * Name of the entity
   * <p>
   * This field represents the name associated with the entity and cannot be null.
   *
   * @see Column
   */
  @Column (name ="name", nullable = false, length = 100)
    String name;
   /**
   * Tag associated with the entity
   * <p>
   * This field represents a tag that is not null and has a maximum length of 100 characters.
   *
   * @see Column
   */
  @Column (name = "tag", nullable = false, length = 100)
    String tag;
   /**
   * Whether the operation should be performed recursively.
   */
  @Column (name = "recursive")
    Boolean recursive;

   /**
   * Creator of the entity, linked by user ID.
   *
   * @see User
   */
  @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "creator_id")
    private User creator = new User();

   /**
   * Duration of the event or task in ISO-8601 format.
   *
   * @see java.time.Instant
   */
  @Column(name = "duration")
    private Instant duration ;

  /**
   * Type of recursive operation
   * <p>
   * Indicates the type of recursive operation to be performed, such as depth-first or breadth-first.
   *
   * @see Column
   */
  @Column(name = "type_of_recursive", length = 5)
    private String typeOfRecursive;

  /**
   * Calendars associated with the event.
   * <p>
   * A set of calendars that this event is linked to, using a many-to-many relationship.
   *
   * @see Calendar
   */
  @ManyToMany
    @JoinTable(name = "calendar_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "calendar_id"))
    private Set<Calendar> calendars = new LinkedHashSet<>();

  /** Constructs a new event object with default values. */
  public Event(){}//default constructor

  /**
   * Constructs an Event object from a given EventProto.
   * <p>
   * Initializes the event with data from the provided EventProto, including name, tag, recursive status,
   * event ID, duration, and type of recursive event. The creator and calendars are set after being retrieved
   * from the database.
   *
   * @param proto The EventProto containing the data to initialize the event.
   */
  public Event(EventProto proto){

        this.name = proto.getName();
        this.tag = proto.getTag();
        this.recursive = proto.getRecursive();
        this.eventId = proto.getId();
        //set creator after user is retrieved from database
        //set calendars after calendar is retrieved from database
        this.duration = proto.getDuration().getSeconds() > 0 ? Instant.ofEpochSecond(proto.getDuration().getSeconds()) : null;//convert it from proto to Instant
        this.typeOfRecursive = ProtoUtilities.parseTypeOfRecursiveToString(proto.getTypeOfRecursive());
    }

  /**
   * Retrieves a set of calendars.
   * <p>
   * Returns the current set of calendars available.
   *
   * @return A set containing the calendars.
   */
  public Set<Calendar> getCalendars() {
        return calendars;
    }

  /**
   * Sets the set of calendars for the current instance.
   *
   * @param calendars The set of calendars to be assigned.
   */
  public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

  /**
   * Retrieves the type of recursive operation.
   * <p>
   * Returns the current type of recursive operation.
   *
   * @return The type of recursive operation.
   */
  public String getTypeOfRecursive() {
        return typeOfRecursive;
    }

  /**
   * Sets the type of recursive operation.
   *
   * @param typeOfRecursive The type of recursive operation to set.
   */
  public void setTypeOfRecursive(String typeOfRecursive) {
        this.typeOfRecursive = typeOfRecursive;
    }

  /**
   * Retrieves the duration.
   * <p>
   * Returns the current duration value.
   *
   * @return The duration.
   */
  public Instant getDuration() {
        return duration;
    }

  /**
   * Sets the duration for the current object.
   *
   * @param duration The new duration value.
   */
  public void setDuration(Instant duration) {
        this.duration = duration;
    }

  /**
   * Retrieves the creator of the current object.
   * <p>
   * Returns the user who created this object.
   *
   * @return The user who created this object.
   */
  public User getCreator() {
        return creator;
    }

  /**
   * Sets the creator of the object.
   * <p>
   * Assigns the provided user as the creator of the current object.
   *
   * @param creator The user who created the object.
   */
  public void setCreator(User creator) {
        this.creator = creator;
    }

  /**
   * Retrieves the name of the object.
   * <p>
   * Returns the value of the 'name' field.
   *
   * @return The name of the object.
   */
  public String getName() {
        return name;
    }

  /**
   * Sets the name for the current object.
   * <p>
   * Updates the internal name property with the provided value.
   *
   * @param name The new name to set.
   */
  public void setName(String name) {
        this.name = name;
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
   * Retrieves the current tag value.
   * <p>
   * Returns the value of the tag attribute.
   *
   * @return The current tag value.
   */
  public String getTag() {
        return tag;
    }

  /**
   * Sets the tag for the current object.
   *
   * @param tag The new tag value.
   */
  public void setTag(String tag) {
        this.tag = tag;
    }

  /**
   * Retrieves whether recursion is enabled.
   * <p>
   * Returns a boolean indicating whether recursion is currently enabled.
   *
   * @return true if recursion is enabled, false otherwise.
   */
  public Boolean getRecursive() {
        return recursive;
    }

  /**
   * Sets whether the operation should be performed recursively.
   *
   * @param recursive Whether the operation should be performed recursively.
   */
  public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

  /**
   * Returns a string representation of the Event object.
   * <p>
   * This method provides a detailed string representation of the Event, including its ID, name, tag,
   * recursive status, duration, and type of recursion.
   *
   * @return A string representation of the Event object.
   */
  @Override
    public String toString() {
        return "Event{" +
                "eventId=" + eventId +
                ", name='" + name + '\'' +
                ", tag='" + tag + '\'' +
                ", recursive=" + recursive +
                ", duration=" + duration +
                ", typeOfRecursive='" + typeOfRecursive + '\'' +
                '}';
    }
}
