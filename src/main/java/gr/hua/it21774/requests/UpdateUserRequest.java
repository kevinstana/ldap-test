package gr.hua.it21774.requests;

public class UpdateUserRequest {
    private Boolean isEnabled;
    
    public UpdateUserRequest(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public UpdateUserRequest() {
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}