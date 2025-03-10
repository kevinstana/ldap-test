package gr.hua.it21774.requests;

public class AssignStudentRequest {
    private Long requestId;
    private Long studentId;
    private Long thesisId;
    private String type;

    public AssignStudentRequest(Long requestId, Long studentId, Long thesisId, String type) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.thesisId = thesisId;
        this.type = type;
    }

    public AssignStudentRequest(Long studentId) {
        this.studentId = studentId;
    }

    public AssignStudentRequest() {
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
