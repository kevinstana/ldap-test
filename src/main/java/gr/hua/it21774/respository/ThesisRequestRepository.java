package gr.hua.it21774.respository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.ThesisRequestsDTO;
import gr.hua.it21774.entities.ThesisRequest;
import jakarta.transaction.Transactional;

@Repository
public interface ThesisRequestRepository extends JpaRepository<ThesisRequest, Long> {

    @Query("SELECT new gr.hua.it21774.dto.ThesisRequestsDTO(" +
            "tr.id, " +
            "tr.thesisId, " +
            "tr.studentId, " +
            "stu.username, " +
            "stu.firstName, " +
            "stu.lastName, " +
            "tr.description, " +
            "tr.pdf, " +
            "tr.pdfSize, " +
            "trs.status, " +
            "tr.createdAt) " +
            "FROM ThesisRequest tr " +
            "JOIN User stu ON stu.id = tr.studentId " +
            "JOIN ThesisRequestStatus trs ON trs.id = tr.statusId " +
            "WHERE tr.thesisId = :thesisId " +
            "")
    Page<ThesisRequestsDTO> getThesisRequests(Pageable pageable, Long thesisId);

    @Transactional
    @Modifying
    @Query("UPDATE ThesisRequest tr SET tr.statusId = :rejectedStatusId WHERE tr.id = :requestId")
    void rejectRequest(Long requestId, Long rejectedStatusId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM thesis_requests WHERE thesis_id = :thesisId", nativeQuery = true)
    void deleteRequestsByThesisId(Long thesisId);

    @Modifying
    @Transactional
    @Query("UPDATE ThesisRequest tr SET tr.statusId = :statusId WHERE tr.thesisId = :thesisId")
    void updateStatusByThesisId(Long thesisId, Long statusId);

}
