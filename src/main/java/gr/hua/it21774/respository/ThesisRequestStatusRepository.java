package gr.hua.it21774.respository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gr.hua.it21774.entities.ThesisRequestStatus;
import gr.hua.it21774.enums.EThesisRequestStatus;

@Repository
public interface ThesisRequestStatusRepository extends JpaRepository<ThesisRequestStatus, Long> {
    @Query("SELECT trs.id FROM ThesisRequestStatus trs WHERE trs.status = :status")
    Optional<Long> findIdByStatus(EThesisRequestStatus status);

    @Query("SELECT trs.status FROM ThesisRequest tr JOIN ThesisRequestStatus trs ON tr.statusId = trs.id WHERE tr.id = :id")
    Optional<EThesisRequestStatus> findStatusByThesisRequestId(Long id);
}
