package via.sep3.viacalendar.services.event;

import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.EventProto;
import via.sep3.viacalendar.gRPC.Calendar.EventProtoList;

/**
 * Event Service Interface
 * <p>
 * Defines the contract for managing events, including creating, updating, deleting, and retrieving event data.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
public interface EventService {
  /**
   * Creates a new event based on the provided payload.
   * <p>
   * This method takes an EventProto object as input and returns a new EventProto object representing the created event.
   *
   * @param payload The payload containing the details of the event to be created.
   * @return A new EventProto object representing the created event.
   */
  EventProto create(EventProto payload);
  /**
   * Updates an event based on the provided payload.
   * <p>
   * Processes the given EventProto payload and updates the corresponding event in the system.
   *
   * @param payload The payload containing the details of the event to be updated.
   */
  void update(EventProto payload);
  /**
   * Deletes an item based on its ID.
   * <p>
   * Removes the item from the system using the provided ID.
   *
   * @param id The ID of the item to be deleted.
   */
  void delete(int id);
   /**
   * Retrieves a single event based on its ID.
   * <p>
   * Fetches an event from the database using the provided ID.
   *
   * @param id The ID of the event to retrieve.
   * @return The event object if found, or null otherwise.
   */
  EventProto getSingle(int id);
  /**
   * Retrieves a list of events.
   * <p>
   * Fetches multiple events from the data source and returns them as a list.
   *
   * @return A list of events.
   */
  EventProtoList getMany();
}
