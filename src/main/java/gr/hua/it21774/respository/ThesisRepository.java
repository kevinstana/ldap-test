package gr.hua.it21774.respository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.dto.ThesisDTO;
import gr.hua.it21774.entities.Thesis;
import gr.hua.it21774.enums.EThesisStatus;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Long> {

    Boolean existsByTitle(String title);

    @Query("SELECT th.id FROM ThesisStatus th WHERE th.status = :status")
    Optional<Long> findIdByStatus(EThesisStatus status);

    @Query("SELECT new gr.hua.it21774.dto.ThesisDTO(" +
            "t.id, " +
            "t.title, " +
            "TO_CHAR(TIMEZONE('Europe/Athens', t.createdAt), 'DD/MM/YYYY'), " +
            "TO_CHAR(TIMEZONE('Europe/Athens', t.lastModified), 'DD/MM/YYYY'), " +
            "CONCAT(u.firstName, ' ', u.lastName), " +
            "ts.status) " +
            "FROM Thesis t " +
            "JOIN User u ON u.id = t.professorId " +
            "JOIN ThesisStatus ts ON ts.id = t.statusId " +
            "WHERE (:query IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "ORDER BY t.id ASC")
    Page<ThesisDTO> customFindAll(Pageable pageable, @Param("query") String query);

    @Query("SELECT new gr.hua.it21774.dto.DetailedThesisDTO(" +
            "t.id, " +
            "t.title, " +
            "t.description, " +
            "CONCAT(prof.firstName, ' ', prof.lastName), " +
            "CONCAT(rev1.firstName, ' ', rev1.lastName), " +
            "CONCAT(rev2.firstName, ' ', rev2.lastName), " +
            "ts.status) " +
            "FROM Thesis t " +
            "JOIN User prof ON prof.id = t.professorId " +
            "JOIN User rev1 ON rev1.id = t.secondReviewerId " +
            "JOIN User rev2 ON rev2.id = t.thirdReviewerId " +
            "JOIN ThesisStatus ts ON ts.id = t.statusId " +
            "WHERE t.id = :id")
    DetailedThesisDTO findThesis(Long id);

}
