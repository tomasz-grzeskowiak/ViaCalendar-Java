package via.sep3.viacalendar.repositories.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import via.sep3.viacalendar.model.Group;

/**
 * Group Repository Interface
 * <p>
 * Provides data access and manipulation capabilities for the {@link Group} entity.
 *
 * @author SEP3 Group 8
 * @version 1.0.0
 * @email "mailto:354782@via.dk"
 * @date 2025.12.15
 * @since 1.0
 */
@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
}
