package via.sep3.viacalendar.repositories.database;

import org.springframework.data.jpa.repository.JpaRepository;
import via.sep3.viacalendar.model.Calendar;

public interface CalendarRepository extends JpaRepository<Calendar, Integer> {
}
