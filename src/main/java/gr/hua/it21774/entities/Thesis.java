package gr.hua.it21774.entities;

import java.time.Instant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "theses", uniqueConstraints = {
        @UniqueConstraint(columnNames = "title"),
        @UniqueConstraint(columnNames = "student_id")
})
public class Thesis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

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
    private double professorGrade;

    @Column(name = "second_reviewer_grade")
    private double secondReviewerGrade;

    @Column(name = "third_reviewer_grade")
    private double thirdReviewerGrade;

    @NotNull
    private int views;

    @Column(name = "doc_link")
    private String docLink;

    public Thesis() {
    }

    public Thesis(String title, String description, Instant createdAt, Instant lastModified,
            Long lastModifiedBy, Long professorId, Long studentId, Long secondReviewerId,
            Long thirdReviewerId, Long statusId, double professorGrade, double secondReviewerGrade,
            double thirdReviewerGrade, int views, String docLink) {
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
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
        this.views = views;
        this.docLink = docLink;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
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

    public double getProfessorGrade() {
        return professorGrade;
    }

    public void setProfessorGrade(double professorGrade) {
        this.professorGrade = professorGrade;
    }

    public double getSecondReviewerGrade() {
        return secondReviewerGrade;
    }

    public void setSecondReviewerGrade(double secondReviewerGrade) {
        this.secondReviewerGrade = secondReviewerGrade;
    }

    public double getThirdReviewerGrade() {
        return thirdReviewerGrade;
    }

    public void setThirdReviewerGrade(double thirdReviewerGrade) {
        this.thirdReviewerGrade = thirdReviewerGrade;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getDocLink() {
        return docLink;
    }

    public void setDocLink(String docLink) {
        this.docLink = docLink;
    }
}
