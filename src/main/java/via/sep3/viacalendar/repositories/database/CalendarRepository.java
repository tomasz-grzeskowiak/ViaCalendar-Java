package via.sep3.viacalendar.repositories.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import via.sep3.viacalendar.model.Calendar;

/**
 * Calendar Repository Interface
 * <p>
 * Provides data access and manipulation for calendar entities.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Repository

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
