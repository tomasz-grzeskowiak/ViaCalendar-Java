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
 * The type Event.
 */
@Entity
@Table (name = "events", schema = "via_calendar")
public class Event {
  /**
   * The Event id.
   */
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer eventId;
  /**
   * The Name.
   */
  @Column (name ="name", nullable = false, length = 100)
    String name;
  /**
   * The Tag.
   */
  @Column (name = "tag", nullable = false, length = 100)
    String tag;
  /**
   * The Recursive.
   */
  @Column (name = "recursive")
    Boolean recursive;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "creator_id")
    private User creator = new User();

    @Column(name = "duration")
    private Instant duration ;

    @Column(name = "type_of_recursive", length = 5)
    private String typeOfRecursive;

    @ManyToMany
    @JoinTable(name = "calendar_events",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "calendar_id"))
    private Set<Calendar> calendars = new LinkedHashSet<>();

  /**
   * Instantiates a new Event.
   */
  public Event(){}//default constructor

  /**
   * Instantiates a new Event.
   *
   * @param proto the proto
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
   * Gets calendars.
   *
   * @return the calendars
   */
  public Set<Calendar> getCalendars() {
        return calendars;
    }

  /**
   * Sets calendars.
   *
   * @param calendars the calendars
   */
  public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

  /**
   * Gets type of recursive.
   *
   * @return the type of recursive
   */
  public String getTypeOfRecursive() {
        return typeOfRecursive;
    }

  /**
   * Sets type of recursive.
   *
   * @param typeOfRecursive the type of recursive
   */
  public void setTypeOfRecursive(String typeOfRecursive) {
        this.typeOfRecursive = typeOfRecursive;
    }

  /**
   * Gets duration.
   *
   * @return the duration
   */
  public Instant getDuration() {
        return duration;
    }

  /**
   * Sets duration.
   *
   * @param duration the duration
   */
  public void setDuration(Instant duration) {
        this.duration = duration;
    }

  /**
   * Gets creator.
   *
   * @return the creator
   */
  public User getCreator() {
        return creator;
    }

  /**
   * Sets creator.
   *
   * @param creator the creator
   */
  public void setCreator(User creator) {
        this.creator = creator;
    }

  /**
   * Gets name.
   *
   * @return the name
   */
  public String getName() {
        return name;
    }

  /**
   * Sets name.
   *
   * @param name the name
   */
  public void setName(String name) {
        this.name = name;
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

  /**
   * Gets tag.
   *
   * @return the tag
   */
  public String getTag() {
        return tag;
    }

  /**
   * Sets tag.
   *
   * @param tag the tag
   */
  public void setTag(String tag) {
        this.tag = tag;
    }

  /**
   * Gets recursive.
   *
   * @return the recursive
   */
  public Boolean getRecursive() {
        return recursive;
    }

  /**
   * Sets recursive.
   *
   * @param recursive the recursive
   */
  public void setRecursive(Boolean recursive) {
        this.recursive = recursive;
    }

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
