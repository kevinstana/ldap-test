package gr.hua.it21774.requests;

public class ThesisStatusChangeRequest {
    private String status;

    public ThesisStatusChangeRequest() {
    }

    public ThesisStatusChangeRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
