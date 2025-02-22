package gr.hua.it21774.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import gr.hua.it21774.entities.CourseTheses;
import jakarta.transaction.Transactional;

@Repository
public interface CourseThesisRepository extends JpaRepository<CourseTheses, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO course_theses (course_id, thesis_id) VALUES (:courseId, :thesisId)", nativeQuery = true)
    void saveCourseThesis(Long courseId, Long thesisId);

    default void saveCoursesForThesis(List<Long> courseIds, Long thesisId) {
        courseIds.forEach(courseId -> saveCourseThesis(courseId, thesisId));
    }
}
