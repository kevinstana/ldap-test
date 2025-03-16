package gr.hua.it21774.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.entities.TaskStatus;
import gr.hua.it21774.enums.ETaskStatus;

@Repository
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Long> {

    @Query("SELECT ts.id FROM TaskStatus ts WHERE ts.status = :status")
    Optional<Long> findIdByStatus(ETaskStatus status);
}
