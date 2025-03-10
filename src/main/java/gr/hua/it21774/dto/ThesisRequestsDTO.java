package gr.hua.it21774.dto;

import java.time.Instant;

import gr.hua.it21774.enums.EThesisRequestStatus;

public class ThesisRequestsDTO {

    private Long id;
    private Long thesisId;
    private Long studentId;
    private String studentUsername;
    private String studentFirstName;
    private String studentLastName;
    private String description;
    private String pdf;
    private Long pdfSize;
    private EThesisRequestStatus status;
    private Instant createdAt;

    public ThesisRequestsDTO() {
    }

    public ThesisRequestsDTO(Long id, Long thesisId, Long studentId, String studentUsername, String studentFirstName,
            String studentLastName,
            String description, String pdf, Long pdfSize, EThesisRequestStatus status, Instant createdAt) {
        this.id = id;
        this.thesisId = thesisId;
        this.studentId = studentId;
        this.studentUsername = studentUsername;
        this.studentFirstName = studentFirstName;
        this.studentLastName = studentLastName;
        this.description = description;
        this.pdf = pdf;
        this.pdfSize = pdfSize;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getThesisId() {
        return thesisId;
    }

    public void setThesisId(Long thesisId) {
        this.thesisId = thesisId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentFirstName() {
        return studentFirstName;
    }

    public void setStudentFirstName(String studentFirstName) {
        this.studentFirstName = studentFirstName;
    }

    public String getStudentLastName() {
        return studentLastName;
    }

    public void setStudentLastName(String studentLastName) {
        this.studentLastName = studentLastName;
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

    public String getPdf() {
        return pdf;
    }

    public void setPdf(String pdf) {
        this.pdf = pdf;
    }

    public Long getPdfSize() {
        return pdfSize;
    }

    public void setPdfSize(Long pdfSize) {
        this.pdfSize = pdfSize;
    }

    public EThesisRequestStatus getStatus() {
        return status;
    }

    public void setStatus(EThesisRequestStatus status) {
        this.status = status;
    }
}