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
   /**
   * Unique identifier for the user
   * <p>
   * This field is used to uniquely identify each user in the system.
   *
   * @see User
   */
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Integer id;
  /**
   * Group associated with the entity.
   * <p>
   * This field represents the group to which the entity belongs. It is lazily loaded and can be null if no group is assigned.
   *
   * @see Group
   */
  @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "group_id")
    private Group group;
   /**
   * Username
   * <p>
   * The username of the user, cannot be null and has a maximum length of 100 characters.
   *
   * @see jakarta.persistence.Column
   */
  @Column(name = "username", nullable = false, length = 100)
    private String username;
   /**
   * Email address of the user
   * <p>
   * This field represents the email address associated with the user and is marked as non-nullable.
   *
   * @see jakarta.persistence.Column
   */
  @Column(name = "email", nullable = false, length = 100)
    private String email;
   /**
   * Password
   * <p>
   * The user's password stored in the database. It is a required field with a maximum length of 100 characters.
   *
   * @see jakarta.persistence.Column
   */
  @Column(name = "password", nullable = false, length = 100)
    private String password;
   /**
   * First name of the user
   * <p>
   * This field stores the first name of the user, which is limited to a maximum length of 100 characters.
   *
   * @see User
   */
  @Column(name = "first_name", length = 100)
    private String firstName;
   /**
   * Last name of the user
   * <p>
   * This field stores the last name of the user, which is used in user identification and display purposes.
   *
   * @see User
   */
  @Column(name = "last_name", length = 100)
    private String lastName;
   /**
   * Calendars associated with the user.
   * <p>
   * Contains a set of calendar entries linked to this user.
   *
   * @see Calendar
   */
  @OneToMany(mappedBy = "user")
    private Set<Calendar> calendars = new LinkedHashSet<>();
   /**
   * Events created by the user
   * <p>
   * A set of events that have been created by this user, maintaining insertion order.
   *
   * @see Event
   */
  @OneToMany(mappedBy = "creator")
    private Set<Event> events = new LinkedHashSet<>();
  /** Constructs a new User object with default values. */
  public User(){} //default constructor
  /**
   * Constructs a new User object from the provided UserProto.
   *
   * @param proto The UserProto containing user data.
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
   * Retrieves the ID of the object.
   * <p>
   * Returns the unique identifier associated with this object.
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
   * Retrieves the current group.
   * <p>
   * Returns the group object that represents the current group.
   *
   * @return The current group.
   */
  public Group getGroup() {
        return group;
    }
  /**
   * Sets the group for the current object.
   *
   * @param group The group to be set.
   */
  public void setGroup(Group group) {
        this.group = group;
    }
  /**
   * Retrieves the current username.
   * <p>
   * Returns the username stored in the instance variable.
   *
   * @return The current username.
   */
  public String getUsername() {
        return username;
    }
  /**
   * Sets the username for the current object.
   * <p>
   * Updates the internal username field with the provided value.
   *
   * @param username The new username to set.
   */
  public void setUsername(String username) {
        this.username = username;
    }
  /**
   * Retrieves the email address.
   * <p>
   * Returns the current email address associated with this object.
   *
   * @return The email address.
   */
  public String getEmail() {
        return email;
    }
  /**
   * Sets the email address for the user.
   * <p>
   * Updates the email address associated with the user.
   *
   * @param email The new email address.
   */
  public void setEmail(String email) {
        this.email = email;
    }
  /**
   * Retrieves the current password.
   * <p>
   * Returns the password stored in the instance.
   *
   * @return The current password.
   */
  public String getPassword() {
        return password;
    }
  /**
   * Sets the password for the current object.
   * <p>
   * Updates the internal password field with the provided value.
   *
   * @param password The new password to set.
   */
  public void setPassword(String password) {
        this.password = password;
    }
  /**
   * Retrieves the first name of the user.
   * <p>
   * Returns the first name stored in the object.
   *
   * @return The first name of the user.
   */
  public String getFirstName() {
        return firstName;
    }
  /**
   * Sets the first name of the user.
   *
   * @param firstName The new first name to be set.
   */
  public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
  /**
   * Retrieves the last name of the user.
   * <p>
   * Returns the last name stored in the object.
   *
   * @return The last name of the user.
   */
  public String getLastName() {
        return lastName;
    }
  /**
   * Sets the last name for the current object.
   *
   * @param lastName The new last name to be set.
   */
  public void setLastName(String lastName) {
        this.lastName = lastName;
    }

  /**
   * Retrieves a set of calendars.
   * <p>
   * Returns the current set of calendars.
   *
   * @return A set containing the calendars.
   */
  public Set<Calendar> getCalendars() {
        return calendars;
    }

  /**
   * Sets the list of calendars.
   * <p>
   * Replaces the current list of calendars with a new one, ensuring uniqueness by using a LinkedHashSet.
   *
   * @param calendars The new list of calendars to set.
   */
  public void setCalendars(List<Calendar> calendars) {
        this.calendars = new LinkedHashSet<>(calendars);

    }

  /**
   * Retrieves a set of events.
   * <p>
   * Returns the current set of events.
   *
   * @return A set containing all events.
   */
  public Set<Event> getEvents() {
        return events;
    }

  /**
   * Sets the list of events.
   * <p>
   * Replaces the current list of events with a new set that maintains insertion order and removes duplicates.
   *
   * @param events The list of events to be set.
   */
  public void setEvents(List<Event> events) {
        this.events = new LinkedHashSet<>(events);
    }

  /**
   * Returns a string representation of the User object.
   * <p>
   * This method provides a detailed string representation of the User, including all its properties.
   *
   * @return A string containing the user's ID, username, email, password, first name, and last name.
   */
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