package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Calendar.
 */
@Entity
@Table(name = "calendars", schema = "via_calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "calendars")
    private Set<Event> events = new LinkedHashSet<>();

  /**
   * Instantiates a new Calendar.
   */
  public Calendar(){}; //default constructor

  /**
   * Instantiates a new Calendar.
   *
   * @param proto the proto
   */
  public Calendar(CalendarProto proto){
    }

  /**
   * Gets events.
   *
   * @return the events
   */
  public Set<Event> getEvents() {
        return events;
    }

  /**
   * Sets events.
   *
   * @param events the events
   */
  public void setEvents(Set<Event> events) {
        this.events = events;
    }

  /**
   * Gets id.
   *
   * @return the id
   */
  public Integer getId() {
        return id;
    }

  /**
   * Sets id.
   *
   * @param id the id
   */
  public void setId(Integer id) {
        this.id = id;
    }

  /**
   * Gets user.
   *
   * @return the user
   */
  public User getUser() {
        return user;
    }

  /**
   * Sets user.
   *
   * @param user the user
   */
  public void setUser(User user) {
        this.user = user;
    }

}