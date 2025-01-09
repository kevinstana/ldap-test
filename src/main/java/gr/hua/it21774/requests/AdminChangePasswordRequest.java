package gr.hua.it21774.requests;

import jakarta.validation.constraints.NotBlank;

public class AdminChangePasswordRequest {

    @NotBlank(message = "New password required")
    private String newPassword;

    @NotBlank(message = "New password verification required")
    private String verifyNewPassword;

    public AdminChangePasswordRequest() {
    }

    public AdminChangePasswordRequest(String newPassword, String verifyNewPassword) {
        this.newPassword = newPassword;
        this.verifyNewPassword = verifyNewPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getVerifyNewPassword() {
        return verifyNewPassword;
    }

    public void setVerifyNewPassword(String verifyNewPassword) {
        this.verifyNewPassword = verifyNewPassword;
    }

    public boolean isPasswordMatch() {
        return newPassword.equals(verifyNewPassword);
    }
}
