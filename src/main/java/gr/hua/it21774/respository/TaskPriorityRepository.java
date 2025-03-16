package gr.hua.it21774.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gr.hua.it21774.entities.TaskPriorityStatus;
import gr.hua.it21774.enums.ETaskPriorityStatus;

public interface TaskPriorityRepository extends JpaRepository<TaskPriorityStatus, Long> {

    @Query("SELECT tp.id FROM TaskPriorityStatus tp WHERE tp.priority = :priority")
    Optional<Long> findIdByStatus(ETaskPriorityStatus priority);
}
