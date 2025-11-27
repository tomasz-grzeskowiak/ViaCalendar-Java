package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.utils.ProtoUtilities;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table (name = "events", schema = "via_calendar")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer eventId;
    @Column (name ="name", nullable = false, length = 100)
    String name;
    @Column (name = "tag", nullable = false, length = 100)
    String tag;
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
    public Event(){}//default constructor
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

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(Set<Calendar> calendars) {
        this.calendars = calendars;
    }

    public String getTypeOfRecursive() {
        return typeOfRecursive;
    }

    public void setTypeOfRecursive(String typeOfRecursive) {
        this.typeOfRecursive = typeOfRecursive;
    }

    public Instant getDuration() {
        return duration;
    }

    public void setDuration(Instant duration) {
        this.duration = duration;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }
    public Boolean getRecursive() {
        return recursive;
    }
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
