package via.sep3.viacalendar.services.group;

import via.sep3.viacalendar.gRPC.Calendar;

/**
 * Group Service Interface
 * <p>
 * Defines the contract for managing group operations, including creating, updating, deleting, and retrieving groups.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
public interface GroupService {
   /**
   * Creates a new Calendar.GroupProto object based on the provided payload.
   *
   * @param payload The payload containing the data for the new group.
   * @return A new Calendar.GroupProto object initialized with the data from the payload.
   */
  Calendar.GroupProto create(Calendar.GroupProto payload);
  /**
   * Updates a calendar group based on the provided payload.
   * <p>
   * This method takes a Calendar.GroupProto object and updates the corresponding calendar group in the system.
   *
   * @param payload The payload containing the details of the calendar group to be updated.
   */
  void update(Calendar.GroupProto payload);
  /**
   * Deletes an item based on its ID.
   * <p>
   * Removes the item from the system using the provided ID.
   *
   * @param id The ID of the item to be deleted.
   */
  void delete(int id);
   /**
   * Retrieves a single group based on its ID.
   * <p>
   * Fetches the group details from the database using the provided ID.
   *
   * @param id The ID of the group to retrieve.
   * @return The group object if found, or null otherwise.
   */
  Calendar.GroupProto getSingle(int id);
   /**
   * Retrieves a list of group protos.
   * <p>
   * Fetches multiple group protos from the data source.
   *
   * @return A list of group protos.
   */
  Calendar.GroupProtoList getMany();
}
