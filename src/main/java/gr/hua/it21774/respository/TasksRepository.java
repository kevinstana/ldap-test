package gr.hua.it21774.respository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.TasksDTO;
import gr.hua.it21774.entities.Task;
import jakarta.transaction.Transactional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

        @Query("SELECT new gr.hua.it21774.dto.TasksDTO(" +
                        "t.id, t.title, t.description, tp.priority, ts.status, t.createdAt) " +
                        "FROM Task t " +
                        "JOIN TaskPriorityStatus tp ON t.priorityId = tp.id " +
                        "JOIN TaskStatus ts ON t.statusId = ts.id " +
                        "WHERE t.thesisId = :thesisId " +
                        "ORDER BY t.createdAt DESC")
        Page<TasksDTO> getTasks(Pageable pageable, Long thesisId);

        @Modifying
        @Transactional
        @Query("DELETE FROM Task t WHERE t.id = :taskId AND t.thesisId = :thesisId")
        void deleteTask(Long taskId, Long thesisId);

        @Modifying
        @Transactional
        @Query("UPDATE Task t SET t.title = :title, t.description = :description, " +
                        "t.priorityId = :priorityId, t.statusId = :statusId " +
                        "WHERE t.id = :taskId AND t.thesisId = :thesisId")
        void updateTask(Long taskId, Long thesisId, String title, String description, Long priorityId, Long statusId);

        @Modifying
        @Transactional
        @Query("DELETE FROM Task t WHERE t.thesisId = :thesisId")
        void deleteAllThesisTasks(Long thesisId);

        @Query("SELECT t.id FROM Task t WHERE t.thesisId = :thesisId")
        List<Long> findTaskIdsByThesisId(Long thesisId);
}
