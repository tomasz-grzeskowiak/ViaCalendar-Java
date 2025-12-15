package via.sep3.viacalendar.services.calendar;

import via.sep3.viacalendar.gRPC.Calendar;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProto;
import via.sep3.viacalendar.gRPC.Calendar.CalendarProtoList;

/**
 * Calendar Service Interface
 * <p>
 * Defines the contract for managing calendar events, including creating, updating, deleting, and retrieving single or multiple events.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
public interface CalendarService {
   /**
   * Creates a new CalendarProto instance based on the provided payload.
   * <p>
   * This method takes a CalendarProto object as input and returns a new CalendarProto object with the same data.
   *
   * @param payload The payload containing the data for the new CalendarProto instance.
   * @return A new CalendarProto instance initialized with the data from the payload.
   */
  CalendarProto create(CalendarProto payload);
  /**
   * Updates the calendar with the provided payload.
   * <p>
   * Processes the given CalendarProto payload and updates the calendar accordingly.
   *
   * @param payload The payload containing the calendar data to be updated.
   */
  void update(CalendarProto payload);
  /**
   * Deletes an item based on its ID.
   * <p>
   * Removes the item from the system using the provided ID.
   *
   * @param id The ID of the item to be deleted.
   */
  void delete(int id);
   /**
   * Retrieves a single CalendarProto object based on its ID.
   * <p>
   * Fetches and returns the CalendarProto object with the specified ID.
   *
   * @param id The ID of the CalendarProto object to retrieve.
   * @return The CalendarProto object corresponding to the given ID, or null if not found.
   */
  CalendarProto getSingle(int id);
   /**
   * Retrieves a list of calendar entries.
   * <p>
   * Fetches multiple calendar entries from the data source and returns them as a list.
   *
   * @return A list of calendar entries.
   */
  CalendarProtoList getMany();
}
