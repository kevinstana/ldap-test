package gr.hua.it21774.respository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.BasicCourseDTO;
import gr.hua.it21774.entities.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT new gr.hua.it21774.dto.BasicCourseDTO(c.id, c.name) FROM Course c " +
            "WHERE c.name NOT IN :excludedNames " +
            "AND (" +
            "    LOWER(TRANSLATE(c.name, 'ΆΈΉΊΌΎΏάέήίόύώ', 'ΑΕΗΙΟΥΩαεηιουω')) LIKE LOWER(TRANSLATE(CONCAT('%', :name, '%'), 'ΆΈΉΊΌΎΏάέήίόύώ', 'ΑΕΗΙΟΥΩαεηιουω')) "
            +
            "    OR LOWER(c.name) LIKE LOWER(CONCAT('%', :englishName, '%')) " +
            ") " +
            "ORDER BY c.name ASC " +
            "LIMIT 5")
    List<BasicCourseDTO> searchCourses(String name, String englishName, List<String> excludedNames);

}
