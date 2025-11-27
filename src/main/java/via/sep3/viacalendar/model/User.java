package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import via.sep3.viacalendar.gRPC.Calendar.UserProto;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * The type User.
 */
@Entity
@Table(name = "users", schema = "via_calendar")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "first_name", length = 100)
    private String firstName;

    @Column(name = "last_name", length = 100)
    private String lastName;

    @OneToMany(mappedBy = "user")
    private Set<Calendar> calendars = new LinkedHashSet<>();

    @OneToMany(mappedBy = "creator")
    private Set<Event> events = new LinkedHashSet<>();

  /**
   * Instantiates a new User.
   */
  public User(){} //default constructor

  /**
   * Constructor for User.
   *
   * @param proto the proto
   * @implNote Set calendars, events and group after retrieved from database manually.
   */
  public User(UserProto proto){
        this.id = proto.getId();
        this.username = proto.getUsername();
        this.email = proto.getEmail();
        this.password = proto.getPassword();
        this.firstName = proto.getFirstName();
        this.lastName = proto.getLastName();
        //set calendars after calendar is retrieved from database
        //set events after event is retrieved from database
        //set group after group is retrieved from database
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
   * Gets group.
   *
   * @return the group
   */
  public Group getGroup() {
        return group;
    }

  /**
   * Sets group.
   *
   * @param group the group
   */
  public void setGroup(Group group) {
        this.group = group;
    }

  /**
   * Gets username.
   *
   * @return the username
   */
  public String getUsername() {
        return username;
    }

  /**
   * Sets username.
   *
   * @param username the username
   */
  public void setUsername(String username) {
        this.username = username;
    }

  /**
   * Gets email.
   *
   * @return the email
   */
  public String getEmail() {
        return email;
    }

  /**
   * Sets email.
   *
   * @param email the email
   */
  public void setEmail(String email) {
        this.email = email;
    }

  /**
   * Gets password.
   *
   * @return the password
   */
  public String getPassword() {
        return password;
    }

  /**
   * Sets password.
   *
   * @param password the password
   */
  public void setPassword(String password) {
        this.password = password;
    }

  /**
   * Gets first name.
   *
   * @return the first name
   */
  public String getFirstName() {
        return firstName;
    }

  /**
   * Sets first name.
   *
   * @param firstName the first name
   */
  public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

  /**
   * Gets last name.
   *
   * @return the last name
   */
  public String getLastName() {
        return lastName;
    }

  /**
   * Sets last name.
   *
   * @param lastName the last name
   */
  public void setLastName(String lastName) {
        this.lastName = lastName;
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
  public void setCalendars(List<Calendar> calendars) {
        this.calendars = new LinkedHashSet<>(calendars);

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
  public void setEvents(List<Event> events) {
        this.events = new LinkedHashSet<>(events);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}