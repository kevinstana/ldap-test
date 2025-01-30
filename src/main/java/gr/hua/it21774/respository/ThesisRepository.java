package gr.hua.it21774.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.entities.Thesis;
import gr.hua.it21774.enums.EThesisStatus;

@Repository
public interface ThesisRepository extends JpaRepository<Thesis, Long> {

    Boolean existsByTitle(String title);

    @Query("SELECT th.id FROM ThesisStatus th WHERE th.status = :status")
    Optional<Long> findIdByStatus(EThesisStatus status);
}
