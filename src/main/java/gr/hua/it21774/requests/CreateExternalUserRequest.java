package gr.hua.it21774.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class CreateExternalUserRequest {

    @NotBlank(message = "Username required")
    private String username;

    @NotBlank(message = "Password required")
    private String password;

    @NotBlank(message = "Password verification required")
    private String verifyPassword;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email required")
    private String email;

    @NotBlank(message = "Role required")
    private String role;

    @NotBlank(message = "First name required")
    private String firstnName;

    @NotBlank(message = "Last name required")
    private String lastName;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        this.verifyPassword = verifyPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFirstnName() {
        return firstnName;
    }

    public void setFirstnName(String firstnName) {
        this.firstnName = firstnName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
