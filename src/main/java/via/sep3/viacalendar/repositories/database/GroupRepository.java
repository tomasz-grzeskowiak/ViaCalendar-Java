package via.sep3.viacalendar.repositories.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import via.sep3.viacalendar.model.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
