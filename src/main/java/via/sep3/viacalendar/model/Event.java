package via.sep3.viacalendar.model;

import jakarta.persistence.*;

@Entity
@Table (name = "event", schema = "via_calendar")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer eventId;
    @Column (name ="name", nullable = false)
    String name;
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
}
