package gr.hua.it21774.requests;

import jakarta.validation.constraints.NotBlank;

public class ChangeMyPasswordRequest {

    @NotBlank(message = "Current password required")
    private String currentPassword;

    @NotBlank(message = "New password required")
    private String newPassword;

    @NotBlank(message = "New password verification required")
    private String verifyNewPassword;

    public ChangeMyPasswordRequest() {
    }

    public ChangeMyPasswordRequest(String currentPassword, String newPassword, String verifyNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.verifyNewPassword = verifyNewPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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
