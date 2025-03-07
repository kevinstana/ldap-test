package gr.hua.it21774.entities;

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

    @Column(name = "status_id")
    private Long statusId;

    public ThesisRequest() {
    }

    public ThesisRequest(Long id, Long studentId, Long thesisId, String description, String pdf, Long statusId) {
        this.id = id;
        this.studentId = studentId;
        this.thesisId = thesisId;
        this.description = description;
        this.pdf = pdf;
        this.statusId = statusId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
