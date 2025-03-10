package gr.hua.it21774.entities;

import java.time.Instant;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "thesis_requests")
public class ThesisRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId;

    @Column(name = "thesis_id")
    private Long thesisId;

    @NotBlank
    private String description;

    @NotBlank
    private String pdf;

    private Long pdfSize;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "created_at")
    private Instant createdAt;

    public ThesisRequest() {
    }

    public ThesisRequest(Long id, Long studentId, Long thesisId, String description, String pdf, Long pdfSize, Long statusId, Instant createdAt) {
        this.id = id;
        this.studentId = studentId;
        this.thesisId = thesisId;
        this.description = description;
        this.pdf = pdf;
        this.pdfSize = pdfSize;
        this.statusId = statusId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getThesisId() {
        return thesisId;
    }

    public void setThesisId(Long thesisId) {
        this.thesisId = thesisId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Long getPdfSize() {
        return pdfSize;
    }

    public void setPdfSize(Long pdfSize) {
        this.pdfSize = pdfSize;
    }
}
