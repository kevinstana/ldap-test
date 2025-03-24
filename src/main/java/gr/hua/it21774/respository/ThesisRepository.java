package gr.hua.it21774.respository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import gr.hua.it21774.dto.DetailedThesisDTO;
import gr.hua.it21774.dto.ThesisDTO;
import gr.hua.it21774.entities.Thesis;
import gr.hua.it21774.enums.EThesisStatus;
import jakarta.transaction.Transactional;

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
                        "WHERE (:query IS NULL OR " +
                        "      LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                        "      LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                        "AND (:statuses IS NULL OR ts.status IN :statuses) " +
                        "AND ts.status <> 'PUBLISHED' " +
                        "ORDER BY " +
                        "         CASE " +
                        "            WHEN ts.status = 'AVAILABLE' THEN 1 " +
                        "            WHEN ts.status = 'IN_PROGRESS' THEN 2 " +
                        "            WHEN ts.status = 'PENDING_REVIEW' THEN 3 " +
                        "            WHEN ts.status = 'REVIEWED' THEN 4 " +
                        "            ELSE 6 " +
                        "         END, " +
                        "         CASE WHEN ts.status = 'AVAILABLE' THEN t.createdAt END DESC, " +
                        "         t.createdAt DESC")
        Page<ThesisDTO> customFindAll(Pageable pageable, String query, List<EThesisStatus> statuses);

        @Query("SELECT new gr.hua.it21774.dto.DetailedThesisDTO(" +
                        "t.id, " +
                        "t.title, " +
                        "t.description, " +
                        "prof.id, " +
                        "prof.firstName, " +
                        "prof.lastName, " +
                        "t.professorGrade, " +
                        "rev1.id, " +
                        "rev1.firstName, " +
                        "rev1.lastName, " +
                        "t.secondReviewerGrade, " +
                        "rev2.id, " +
                        "rev2.firstName, " +
                        "rev2.lastName, " +
                        "t.thirdReviewerGrade, " +
                        "stu.id, " +
                        "stu.firstName, " +
                        "stu.lastName, " +
                        "ts.status, " +
                        "t.publishedAt, " +
                        "t.fileName, " +
                        "t.fileSize) " +
                        "FROM Thesis t " +
                        "JOIN User prof ON prof.id = t.professorId " +
                        "JOIN User rev1 ON rev1.id = t.secondReviewerId " +
                        "JOIN User rev2 ON rev2.id = t.thirdReviewerId " +
                        "LEFT JOIN User stu ON stu.id = t.studentId " +
                        "JOIN ThesisStatus ts ON ts.id = t.statusId " +
                        "WHERE t.id = :id")
        DetailedThesisDTO findThesis(Long id);

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
                        "WHERE (:id IS NULL OR t.professorId = :id) " +
                        "AND (:query IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                        "AND (:statuses IS NULL OR ts.status IN :statuses) " +
                        "ORDER BY " +
                        "         CASE " +
                        "            WHEN ts.status = 'AVAILABLE' THEN 1 " +
                        "            WHEN ts.status = 'IN_PROGRESS' THEN 2 " +
                        "            WHEN ts.status = 'PENDING_REVIEW' THEN 3 " +
                        "            WHEN ts.status = 'REVIEWED' THEN 4 " +
                        "            ELSE 6 " +
                        "         END, " +
                        "         CASE WHEN ts.status = 'AVAILABLE' THEN t.createdAt END DESC, " +
                        "         t.createdAt DESC")
        Page<ThesisDTO> customFindAllByTeacherId(Pageable pageable, Long id, String query,
                        List<EThesisStatus> statuses);

        @Modifying
        @Query("DELETE FROM CourseTheses c WHERE c.thesisId = :thesisId")
        void deleteThesisCourseRelationships(Long thesisId);

        @Modifying
        @Query("UPDATE Thesis t SET t.title = :title, t.description = :description, " +
                        "t.secondReviewerId = :secondReviewerId, t.thirdReviewerId = :thirdReviewerId, t.lastModified = :lastModified "
                        +
                        "WHERE t.id = :id")
        void updateThesisDetails(Long id, String title, String description, Long secondReviewerId,
                        Long thirdReviewerId, Instant lastModified);

        @Query("SELECT t.id FROM Thesis t WHERE t.title = :title")
        Optional<Long> findIdByTitle(String title);

        @Query("SELECT COUNT(tr) > 0 FROM ThesisRequest tr WHERE tr.studentId = :studentId AND tr.thesisId = :thesisId")
        Boolean hasMadeRequest(Long studentId, Long thesisId);

        @Query("SELECT COUNT(t) > 0 FROM Thesis t WHERE t.studentId = :studentId")
        Boolean hasStudentThesis(Long studentId);

        @Transactional
        @Modifying
        @Query("UPDATE ThesisRequest tr SET tr.statusId = :statusId WHERE tr.id = :requestId")
        void changeRequestStatus(Long statusId, Long requestId);

        @Transactional
        @Modifying
        @Query("UPDATE ThesisRequest tr SET tr.statusId = :statusId WHERE tr.thesisId = :thesisId")
        void changeAllThesisRequestStatus(Long thesisId, Long statusId);

        @Transactional
        @Modifying
        @Query("UPDATE ThesisRequest tr SET tr.statusId = :statusId WHERE tr.studentId = :studentId")
        void changeRequestStatusByStudentId(Long studentId, Long statusId);

        @Transactional
        @Modifying
        @Query("UPDATE ThesisRequest tr SET tr.statusId = :invalidatedStatusId WHERE tr.studentId = :studentId AND tr.id <> :approvedRequestId")
        void invalidateOtherRequestsByStudent(Long studentId, Long approvedRequestId, Long invalidatedStatusId);

        @Transactional
        @Modifying
        @Query("UPDATE ThesisRequest tr SET tr.statusId = :rejectedStatusId WHERE tr.thesisId = :thesisId AND tr.id <> :requestId")
        void rejectOtherRequests(Long requestId, Long thesisId, Long rejectedStatusId);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.statusId = :statusId, t.studentId = :studentId WHERE t.id = :thesisId")
        void updateThesisStatus(Long thesisId, Long studentId, Long statusId);

        @Query("SELECT ts.status FROM Thesis t " +
                        "JOIN ThesisStatus ts ON ts.id = t.statusId " +
                        "WHERE t.id = :thesisId")
        EThesisStatus getThesisStatus(Long thesisId);

        @Query("SELECT new gr.hua.it21774.dto.DetailedThesisDTO(" +
                        "t.id, " +
                        "t.title, " +
                        "t.description, " +
                        "prof.id, " +
                        "prof.firstName, " +
                        "prof.lastName, " +
                        "t.professorGrade, " +
                        "rev1.id, " +
                        "rev1.firstName, " +
                        "rev1.lastName, " +
                        "t.secondReviewerGrade, " +
                        "rev2.id, " +
                        "rev2.firstName, " +
                        "rev2.lastName, " +
                        "t.thirdReviewerGrade, " +
                        "stu.id, " +
                        "stu.firstName, " +
                        "stu.lastName, " +
                        "ts.status, " +
                        "t.publishedAt, " +
                        "t.fileName, " +
                        "t.fileSize) " +
                        "FROM Thesis t " +
                        "JOIN User prof ON prof.id = t.professorId " +
                        "JOIN User rev1 ON rev1.id = t.secondReviewerId " +
                        "JOIN User rev2 ON rev2.id = t.thirdReviewerId " +
                        "LEFT JOIN User stu ON stu.id = t.studentId " +
                        "JOIN ThesisStatus ts ON ts.id = t.statusId " +
                        "WHERE t.studentId = :id")
        DetailedThesisDTO getMyAssignment(Long id);

        @Query("SELECT DISTINCT CASE " +
                        "WHEN LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
                        "THEN t.title " +
                        "ELSE CONCAT(prof.firstName, ' ', prof.lastName) " +
                        "END " +
                        "FROM Thesis t " +
                        "JOIN User prof ON prof.id = t.professorId " +
                        "WHERE LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
                        "OR LOWER(CONCAT(prof.firstName, ' ', prof.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) ")
        List<String> searchTheses(String query, Pageable pageable);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.statusId = :statusId, t.studentId = :studentId, t.lastModified = :lastModified WHERE t.id = :thesisId")
        void updateThesisStatus(Long thesisId, Long studentId, Long statusId, Instant lastModified);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.statusId = :statusId, t.studentId = :studentId, t.lastModified = :lastModified, t.startedAt = :lastModified WHERE t.id = :thesisId")
        void assignStudent(Long thesisId, Long studentId, Long statusId, Instant lastModified);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.statusId = :statusId, t.lastModified = :lastModified WHERE t.id = :thesisId")
        void updateThesisStatus(Long thesisId, Long statusId, Instant lastModified);

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
                        "WHERE (t.secondReviewerId = :reviewerId OR t.thirdReviewerId = :reviewerId) " +
                        "AND (:query IS NULL OR LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
                        "OR LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                        "AND (:statuses IS NULL OR ts.status IN :statuses) " +
                        "ORDER BY " +
                        "         CASE " +
                        "            WHEN ts.status = 'AVAILABLE' THEN 1 " +
                        "            WHEN ts.status = 'IN_PROGRESS' THEN 2 " +
                        "            WHEN ts.status = 'PENDING_REVIEW' THEN 3 " +
                        "            WHEN ts.status = 'REVIEWED' THEN 4 " +
                        "            ELSE 6 " +
                        "         END, " +
                        "         CASE WHEN ts.status = 'AVAILABLE' THEN t.createdAt END DESC, " +
                        "         t.createdAt DESC")
        Page<ThesisDTO> getMyAssignedReviews(Pageable pageable, Long reviewerId, String query,
                        List<EThesisStatus> statuses);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.secondReviewerGrade = :grade WHERE t.id = :thesisId")
        void secondReviewerGrade(Long thesisId, Double grade);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.thirdReviewerGrade = :grade WHERE t.id = :thesisId")
        void thirdReviewerGrade(Long thesisId, Double grade);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.professorGrade = :grade WHERE t.id = :thesisId")
        void professorGrade(Long thesisId, Double grade);

        @Transactional
        @Modifying
        @Query("UPDATE Thesis t SET t.statusId = :statusId, t.fileName = :fileName, t.fileSize = :fileSize, t.lastModified = :lastModified, t.publishedAt = :lastModified WHERE t.id = :thesisId")
        void publishThesis(Long thesisId, Long statusId, String fileName, Long fileSize, Instant lastModified);

        @Query("SELECT new gr.hua.it21774.dto.DetailedThesisDTO(" +
                        "t.id, " +
                        "t.title, " +
                        "t.description, " +
                        "u.id, " +
                        "u.firstName, " +
                        "u.lastName, " +
                        "t.professorGrade, " +
                        "r1.id, " +
                        "r1.firstName, " +
                        "r1.lastName, " +
                        "t.secondReviewerGrade, " +
                        "r2.id, " +
                        "r2.firstName, " +
                        "r2.lastName, " +
                        "t.thirdReviewerGrade, " +
                        "s.id, " +
                        "s.firstName, " +
                        "s.lastName, " +
                        "ts.status, " +
                        "t.publishedAt, " +
                        "t.fileName, " +
                        "t.fileSize) " +
                        "FROM Thesis t " +
                        "JOIN User u ON u.id = t.professorId " +
                        "JOIN User r1 ON r1.id = t.secondReviewerId " +
                        "JOIN User r2 ON r2.id = t.thirdReviewerId " +
                        "JOIN User s ON s.id = t.studentId " +
                        "JOIN ThesisStatus ts ON ts.id = t.statusId " +
                        "WHERE (:query IS NULL OR " +
                        "      LOWER(t.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                        "      LOWER(CONCAT(u.firstName, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
                        "      LOWER(CONCAT(s.firstName, ' ', s.lastName)) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                        "AND ts.status = 'PUBLISHED' " +
                        "ORDER BY " +
                        "         CASE " +
                        "            WHEN ts.status = 'PUBLISHED' THEN 1 " +
                        "            ELSE 2 " +
                        "         END, " +
                        "         t.publishedAt DESC")
        Page<DetailedThesisDTO> customFindPublishedTheses(Pageable pageable, String query);

        @Query("SELECT COUNT(t) > 0 FROM Thesis t " +
                        "JOIN ThesisStatus ts ON ts.id = t.statusId " +
                        "WHERE ts.status = 'PUBLISHED'")
        Boolean existsPublishedTheses();

}
