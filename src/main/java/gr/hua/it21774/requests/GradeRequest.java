package gr.hua.it21774.requests;

public class GradeRequest {
    private Double grade;

    public GradeRequest(Double grade) {
        this.grade = grade;
    }

    public GradeRequest() {
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }
}
