package gr.hua.it21774.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "course_theses")
public class CourseTheses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private Long courseId;

    @Column(name = "thesis_id", nullable = false)
    private Long thesisId;

    public CourseTheses() {
    }

    public CourseTheses(Long courseId, Long thesisId) {
        this.courseId = courseId;
        this.thesisId = thesisId;
    }

    public CourseTheses(Long id, Long courseId, Long thesisId) {
        this.id = id;
        this.courseId = courseId;
        this.thesisId = thesisId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getThesisId() {
        return thesisId;
    }

    public void setThesisId(Long thesisId) {
        this.thesisId = thesisId;
    }
}
