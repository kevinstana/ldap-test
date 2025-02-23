package gr.hua.it21774.dto;

import gr.hua.it21774.enums.EThesisStatus;

public class ThesisDTO {

    private Long id;
    private String title;
    private String createdAt;
    private String lastModified;
    private String professorFullName;
    private EThesisStatus status;

    public ThesisDTO() {
    }

    public ThesisDTO(Long id, String title, String createdAt, String lastModified, String professorFullName,
    EThesisStatus status) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.professorFullName = professorFullName;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getLastModified() {
        return lastModified;
    }

    public String getProfessorFullName() {
        return professorFullName;
    }

    public EThesisStatus getStatus() {
        return status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public void setProfessorFullName(String professorFullName) {
        this.professorFullName = professorFullName;
    }

    public void setStatus(EThesisStatus status) {
        this.status = status;
    }
}
