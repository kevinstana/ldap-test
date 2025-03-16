package gr.hua.it21774.respository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.TaskFilesDTO;
import gr.hua.it21774.entities.TaskFiles;
import jakarta.transaction.Transactional;

@Repository
public interface TaskFilesRepository extends JpaRepository<TaskFiles, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO task_files (task_id, filename, filesize) VALUES (:taskId, :fileName, :fileSize)", nativeQuery = true)
    void saveTaskFile(Long taskId, String fileName, Long fileSize);

    default void saveTaskFiles(Long taskId, Map<String, Long> files) {
        files.forEach((fileName, fileSize) -> saveTaskFile(taskId, fileName, fileSize));
    }

    @Query("SELECT new gr.hua.it21774.dto.TaskFilesDTO(tf.fileName, tf.fileSize) FROM TaskFiles tf WHERE tf.taskId = :taskId")
    List<TaskFilesDTO> getTaskFiles(Long taskId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM task_files WHERE task_id = :taskId", nativeQuery = true)
    void deleteTaskFiles(Long taskId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM task_files WHERE task_id = :taskId AND filename IN :fileNames", nativeQuery = true)
    void deleteTaskFilesByNames(Long taskId, Set<String> fileNames);

}
