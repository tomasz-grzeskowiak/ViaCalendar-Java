package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import via.sep3.viacalendar.gRPC.Calendar.UserProto;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    public User(){} //default constructor

    /**
     * Constructor for User.
     * @implNote Set calendars, events and group after retrieved from database manually.
     * @param proto
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Set<Calendar> getCalendars() {
        return calendars;
    }

    public void setCalendars(List<Calendar> calendars) {
        this.calendars = new LinkedHashSet<>(calendars);

    }

    public Set<Event> getEvents() {
        return events;
    }

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