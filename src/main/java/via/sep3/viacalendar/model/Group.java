package via.sep3.viacalendar.model;

import jakarta.persistence.*;
import via.sep3.viacalendar.gRPC.Calendar.GroupProto;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Group Entity Class
 * <p>
 * Represents a group entity in the database, including its ID, name, and associated users.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */

@Entity
@Table(name = "groups", schema = "via_calendar")
public class Group {
  /**
   * Group identifier
   * <p>
   * Unique identifier for the group, auto-generated.
   *
   * @see jakarta.persistence.GeneratedValue
   * @see jakarta.persistence.GenerationType
   */
  @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", nullable = false)
    private Integer id;

   /**
   * Name of the entity
   * <p>
   * This field represents the name associated with the entity and is mandatory.
   *
   * @see jakarta.persistence.Column
   */
  @Column(name = "name", nullable = false, length = 100)
    private String name;

   /**
   * Creator ID
   * <p>
   * Unique identifier for the user who created the record.
   *
   * @see Column
   */
  @Column(name = "creator_id", nullable = false)
    private Integer creator_id;

   /**
   * Users associated with this group.
   * <p>
   * A set of users that belong to the current group, stored in a linked hash set for predictable iteration order.
   *
   * @see User
   */
  @OneToMany(mappedBy = "group")
    private Set<User> users = new LinkedHashSet<>();

  /** Constructs a new instance of the Group class. */
  public Group(){};//default

  /**
   * Constructs a new Group object from a GroupProto.
   *
   * @param proto The protobuf representation of the group.
   */
  public Group(GroupProto proto){
        id = proto.getId();
        name = proto.getName();
    };

  /**
   * Retrieves a set of all users.
   * <p>
   * Returns the current collection of users.
   *
   * @return A set containing all users.
   */
  public Set<User> getUsers() {
        return users;
    }

  /**
   * Sets the collection of users.
   *
   * @param users The new set of users to be assigned.
   */
  public void setUsers(Set<User> users) {
        this.users = users;
    }

  /**
   * Retrieves the ID of the object.
   * <p>
   * Returns the unique identifier for this object.
   *
   * @return The ID of the object.
   */
  public Integer getId() {
        return id;
    }

  /**
   * Sets the ID for the current object.
   * <p>
   * Updates the internal ID with the provided value.
   *
   * @param id The new ID to set.
   */
  public void setId(Integer id) {
        this.id = id;
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
   * Sets the name of the object.
   * <p>
   * Updates the internal name property with the provided value.
   *
   * @param name The new name for the object.
   */
  public void setName(String name) {
        this.name = name;
    }

  /**
   * Retrieves the creator ID.
   * <p>
   * Returns the ID of the creator associated with this object.
   *
   * @return The creator's ID.
   */
  public  Integer getCreator_id() {
        return creator_id;
  }

  /**
   * Sets the ID of the creator.
   *
   * @param creator_id The ID of the creator.
   */
  public void setCreator_id(Integer creator_id) {
      this.creator_id = creator_id;
  }
}