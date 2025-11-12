package via.sep3.viacalendar.model;

import jakarta.persistence.*;

@Entity
@Table (name = "event", schema = "via_calendar")
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

}
