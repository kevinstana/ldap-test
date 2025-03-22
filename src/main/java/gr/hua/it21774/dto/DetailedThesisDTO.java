package gr.hua.it21774.dto;

import java.time.Instant;
import gr.hua.it21774.enums.EThesisStatus;

public class DetailedThesisDTO {

    private Long id;
    private String title;
    private String description;

    private Long professorId;
    private String professorFirstName;
    private String professorLastName;
    private Double professorGrade;

    private Long reviewer1Id;
    private String reviewer1FirstName;
    private String reviewer1LastName;
    private Double reviewer1Grade;

    private Long reviewer2Id;
    private String reviewer2FirstName;
    private String reviewer2LastName;
    private Double reviewer2Grade;

    private Long studentId;
    private String studentFirstName;
    private String studentLastName;

    private EThesisStatus status;
    private Instant publishedAt;

    private String fileName;
    private Long fileSize;

    public DetailedThesisDTO() {
    }

    public DetailedThesisDTO(Long id, String title, String description,
            Long professorId, String professorFirstName, String professorLastName, Double professorGrade,
            Long reviewer1Id, String reviewer1FirstName, String reviewer1LastName, Double reviewer1Grade,
            Long reviewer2Id, String reviewer2FirstName, String reviewer2LastName, Double reviewer2Grade,
            Long studentId, String studentFirstName, String studentLastName,
            EThesisStatus status, Instant publishedAt, String fileName, Long fileSize) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.professorId = professorId;
        this.professorFirstName = professorFirstName;
        this.professorLastName = professorLastName;
        this.professorGrade = professorGrade;
        this.reviewer1Id = reviewer1Id;
        this.reviewer1FirstName = reviewer1FirstName;
        this.reviewer1LastName = reviewer1LastName;
        this.reviewer1Grade = reviewer1Grade;
        this.reviewer2Id = reviewer2Id;
        this.reviewer2FirstName = reviewer2FirstName;
        this.reviewer2LastName = reviewer2LastName;
        this.reviewer2Grade = reviewer2Grade;
        this.studentId = studentId != null ? studentId : null;
        this.studentFirstName = studentFirstName != null ? studentFirstName : null;
        this.studentLastName = studentLastName != null ? studentLastName : null;
        this.status = status;
        this.publishedAt = publishedAt;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Double getProfessorGrade() {
        return professorGrade;
    }

    public void setProfessorGrade(Double professorGrade) {
        this.professorGrade = professorGrade;
    }

    public Double getReviewer1Grade() {
        return reviewer1Grade;
    }

    public void setReviewer1Grade(Double reviewer1Grade) {
        this.reviewer1Grade = reviewer1Grade;
    }

    public Double getReviewer2Grade() {
        return reviewer2Grade;
    }

    public void setReviewer2Grade(Double reviewer2Grade) {
        this.reviewer2Grade = reviewer2Grade;
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

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorFirstName() {
        return professorFirstName;
    }

    public void setProfessorFirstName(String professorFirstName) {
        this.professorFirstName = professorFirstName;
    }

    public String getProfessorLastName() {
        return professorLastName;
    }

    public void setProfessorLastName(String professorLastName) {
        this.professorLastName = professorLastName;
    }

    public Long getReviewer1Id() {
        return reviewer1Id;
    }

    public void setReviewer1Id(Long reviewer1Id) {
        this.reviewer1Id = reviewer1Id;
    }

    public String getReviewer1FirstName() {
        return reviewer1FirstName;
    }

    public void setReviewer1FirstName(String reviewer1FirstName) {
        this.reviewer1FirstName = reviewer1FirstName;
    }

    public String getReviewer1LastName() {
        return reviewer1LastName;
    }

    public void setReviewer1LastName(String reviewer1LastName) {
        this.reviewer1LastName = reviewer1LastName;
    }

    public Long getReviewer2Id() {
        return reviewer2Id;
    }

    public void setReviewer2Id(Long reviewer2Id) {
        this.reviewer2Id = reviewer2Id;
    }

    public String getReviewer2FirstName() {
        return reviewer2FirstName;
    }

    public void setReviewer2FirstName(String reviewer2FirstName) {
        this.reviewer2FirstName = reviewer2FirstName;
    }

    public String getReviewer2LastName() {
        return reviewer2LastName;
    }

    public void setReviewer2LastName(String reviewer2LastName) {
        this.reviewer2LastName = reviewer2LastName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
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

    public EThesisStatus getStatus() {
        return status;
    }

    public void setStatus(EThesisStatus status) {
        this.status = status;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }
}
