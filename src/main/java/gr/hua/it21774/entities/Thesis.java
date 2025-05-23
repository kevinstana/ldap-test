package gr.hua.it21774.entities;

import java.time.Instant;

import gr.hua.it21774.enums.EThesisStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "theses")
public class Thesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 256, message = "Title cannot be longer than 256 characters.")
    private String title;

    @NotBlank
    private String description;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Column(name = "started_at")
    private Instant startedAt;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "professor_id")
    private Long professorId;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "second_reviewer_id")
    private Long secondReviewerId;

    @Column(name = "third_reviewer_id")
    private Long thirdReviewerId;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "professor_grade")
    private Double professorGrade;

    @Column(name = "second_reviewer_grade")
    private Double secondReviewerGrade;

    @Column(name = "third_reviewer_grade")
    private Double thirdReviewerGrade;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_size")
    private Long fileSize;

    @Column(name = "published_at")
    private Instant publishedAt;

    public Thesis() {
    }

    public Thesis(String title, String description, Long createdBy, Instant createdAt,
            Instant lastModified,
            Instant startedAt,
            Long lastModifiedBy, Long professorId, Long studentId, Long secondReviewerId,
            Long thirdReviewerId, Long statusId, Double professorGrade, Double secondReviewerGrade,
            Double thirdReviewerGrade, String fileName, Long fileSize, Instant publishedAt) {
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.startedAt = startedAt;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.professorId = professorId;
        this.studentId = studentId;
        this.secondReviewerId = secondReviewerId;
        this.thirdReviewerId = thirdReviewerId;
        this.statusId = statusId;
        this.professorGrade = professorGrade;
        this.secondReviewerGrade = secondReviewerGrade;
        this.thirdReviewerGrade = thirdReviewerGrade;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.publishedAt = publishedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(Instant startedAt) {
        this.startedAt = startedAt;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public void setLastModified(Instant lastModified) {
        this.lastModified = lastModified;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getSecondReviewerId() {
        return secondReviewerId;
    }

    public void setSecondReviewerId(Long secondReviewerId) {
        this.secondReviewerId = secondReviewerId;
    }

    public Long getThirdReviewerId() {
        return thirdReviewerId;
    }

    public void setThirdReviewerId(Long thirdReviewerId) {
        this.thirdReviewerId = thirdReviewerId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Double getProfessorGrade() {
        return professorGrade;
    }

    public void setProfessorGrade(Double professorGrade) {
        this.professorGrade = professorGrade;
    }

    public Double getSecondReviewerGrade() {
        return secondReviewerGrade;
    }

    public void setSecondReviewerGrade(Double secondReviewerGrade) {
        this.secondReviewerGrade = secondReviewerGrade;
    }

    public Double getThirdReviewerGrade() {
        return thirdReviewerGrade;
    }

    public void setThirdReviewerGrade(Double thirdReviewerGrade) {
        this.thirdReviewerGrade = thirdReviewerGrade;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public static boolean isValidStatus(String value) {
        if (value == null) {
            return false;
        }

        for (EThesisStatus status : EThesisStatus.values()) {
            if (status.name().equals(value)) {
                return true;
            }
        }
        return false;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
