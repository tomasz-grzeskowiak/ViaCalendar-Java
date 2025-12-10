package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import via.sep3.viacalendar.gRPC.Calendar.GroupProto;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The type Group.
 */
@Entity
@Table(name = "groups", schema = "via_calendar")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "creator_id", nullable = false)
    private Integer creator_id;

    @OneToMany(mappedBy = "group")
    private Set<User> users = new LinkedHashSet<>();

  /**
   * Instantiates a new Group.
   */
  public Group(){};//default

  /**
   * Constructor for Group.
   *
   * @param proto the proto
   * @implNote Set users gotten from the database manually.
   */
  public Group(GroupProto proto){
        id = proto.getId();
        name = proto.getName();
    };

  /**
   * Gets users.
   *
   * @return the users
   */
  public Set<User> getUsers() {
        return users;
    }

  /**
   * Sets users.
   *
   * @param users the users
   */
  public void setUsers(Set<User> users) {
        this.users = users;
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

  public  Integer getCreator_id() {
        return creator_id;
  }
  public void setCreator_id(Integer creator_id) {
      this.creator_id = creator_id;
  }
}