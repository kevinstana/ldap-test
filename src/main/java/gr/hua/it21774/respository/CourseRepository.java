package gr.hua.it21774.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.BasicCourseDTO;
import gr.hua.it21774.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT new gr.hua.it21774.dto.BasicCourseDTO(c.id, c.name) FROM Course c " +
            "WHERE c.name NOT IN :excludedNames " +
            "AND (" +
            "    LOWER(TRANSLATE(c.name, 'ΆΈΉΊΌΎΏάέήίόύώς', 'ΑΕΗΙΟΥΩαεηιουωσ')) LIKE LOWER(TRANSLATE(CONCAT('%', :name, '%'), 'ΆΈΉΊΌΎΏάέήίόύώς', 'ΑΕΗΙΟΥΩαεηιουωσ')) "
            +
            "    OR LOWER(c.name) LIKE LOWER(CONCAT('%', :englishName, '%')) " +
            ") " +
            "ORDER BY c.name ASC " +
            "LIMIT 5")
    List<BasicCourseDTO> searchCourses(String name, String englishName, List<String> excludedNames);

    @Query("SELECT new gr.hua.it21774.entities.Course(c.id, c.name, c.url) " +
            "FROM Course c " +
            "JOIN CourseTheses ct ON ct.courseId = c.id " +
            "WHERE ct.thesisId = :thesisId " +
            "ORDER BY c.name ASC")
    List<Course> getThesisCourses(@Param("thesisId") Long thesisId);

}
