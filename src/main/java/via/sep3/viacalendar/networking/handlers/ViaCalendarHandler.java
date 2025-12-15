package via.sep3.viacalendar.networking.handlers;

import com.google.protobuf.Message;
import org.springframework.stereotype.Service;
import via.sep3.viacalendar.gRPC.Calendar.HandlerTypeProto;
import via.sep3.viacalendar.gRPC.Calendar.ActionTypeProto;

/**
 * Interface for handling calendar actions.
 * <p>
 * Defines methods for processing different types of calendar actions and retrieving the handler type.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
public interface ViaCalendarHandler {
  /**
   * Handles a message based on the provided action type and payload.
   * <p>
   * Processes the given action type and payload according to the specified business logic.
   *
   * @param actionType The type of action to be handled.
   * @param payload    The data associated with the action.
   * @return A response object indicating the outcome of the message handling.
   * @throws IllegalArgumentException If the action type is not supported or the payload is invalid.
   */
  Message handle(ActionTypeProto actionType, Object payload);
  /**
   * Retrieves the type of handler as a Protocol Buffers message.
   * <p>
   * Returns the handler type in Protocol Buffers format.
   *
   * @return The handler type as a HandlerTypeProto message.
   */
  HandlerTypeProto getType();
}
