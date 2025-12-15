package via.sep3.viacalendar.services.user;

import via.sep3.viacalendar.gRPC.Calendar.UserProto;
import via.sep3.viacalendar.gRPC.Calendar.UserProtoList;

/**
 * User Service Interface
 * <p>
 * Defines the contract for user-related operations, including creating, updating, deleting, and retrieving users.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
public interface UserService {
  /**
   * Creates a new UserProto based on the provided payload.
   * <p>
   * This method takes a UserProto object as input and returns a new UserProto object with updated information.
   *
   * @param payload The payload containing user data.
   * @return A new UserProto object representing the created user.
   */
  UserProto create(UserProto payload);
  /**
   * Updates a user based on the provided payload.
   * <p>
   * This method takes a UserProto object and updates the corresponding user in the system.
   *
   * @param payload The UserProto object containing the updated user information.
   */
  void update(UserProto payload);
  /**
   * Deletes an item based on its ID.
   * <p>
   * Removes the item from the system using the provided ID.
   *
   * @param id The ID of the item to be deleted.
   */
  void delete(int id);
  /**
   * Retrieves a single UserProto object based on its ID.
   * <p>
   * Fetches and returns the UserProto object with the specified ID.
   *
   * @param id The ID of the UserProto to retrieve.
   * @return The UserProto object corresponding to the given ID, or null if not found.
   */
  UserProto getSingle(int id);
  /**
   * Retrieves a list of users from the service.
   * <p>
   * Fetches multiple users and returns them as a list.
   *
   * @return A list of users.
   */
  UserProtoList getMany();
}
