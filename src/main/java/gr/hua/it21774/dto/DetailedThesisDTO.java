package gr.hua.it21774.dto;

import gr.hua.it21774.enums.EThesisStatus;

public class DetailedThesisDTO {

    private Long id;
    private String title;
    private String description;
    private String professorFullName;
    private String reviewer1FullName;
    private String reviewer2FullName;
    private Long professorId;
    private Long reviewer1Id;
    private Long reviewer2Id;
    private EThesisStatus status;

    public DetailedThesisDTO() {
    }

    public DetailedThesisDTO(Long id, String title, String description, String professorFullName,
            String reviewer1FullName, String reviewer2FullName, Long professorId, Long reviewer1Id, Long reviewer2Id, EThesisStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.professorFullName = professorFullName;
        this.reviewer1FullName = reviewer1FullName;
        this.reviewer2FullName = reviewer2FullName;
        this.professorId = professorId;
        this.reviewer1Id = reviewer1Id;
        this.reviewer2Id = reviewer2Id;
        this.status = status;
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

    public String getProfessorFullName() {
        return professorFullName;
    }

    public void setProfessorFullName(String professorFullName) {
        this.professorFullName = professorFullName;
    }

    public String getReviewer1FullName() {
        return reviewer1FullName;
    }

    public void setReviewer1FullName(String reviewer1FullName) {
        this.reviewer1FullName = reviewer1FullName;
    }

    public String getReviewer2FullName() {
        return reviewer2FullName;
    }

    public void setReviewer2FullName(String reviewer2FullName) {
        this.reviewer2FullName = reviewer2FullName;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long getReviewer1Id() {
        return reviewer1Id;
    }

    public void setReviewer1Id(Long reviewer1Id) {
        this.reviewer1Id = reviewer1Id;
    }

    public Long getReviewer2Id() {
        return reviewer2Id;
    }

    public void setReviewer2Id(Long reviewer2Id) {
        this.reviewer2Id = reviewer2Id;
    }

    public EThesisStatus getStatus() {
        return status;
    }

    public void setStatus(EThesisStatus status) {
        this.status = status;
    }
}
