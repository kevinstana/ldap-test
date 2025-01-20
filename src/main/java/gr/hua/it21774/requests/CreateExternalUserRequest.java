package gr.hua.it21774.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateExternalUserRequest {

    @NotBlank(message = "Username required")
    @Size(max = 256, message = "Username cannot be longer than 256 characters.")
    private String username;

    @NotBlank(message = "Password required")
    @Size(max = 256, message = "Password cannot be longer than 256 characters.")
    private String password;

    @NotBlank(message = "Password verification required")
    @Size(max = 256, message = "Password verification cannot be longer than 256 characters.")
    private String verifyPassword;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email required")
    @Size(max = 256, message = "Email cannot be longer than 256 characters.")
    private String email;

    @NotBlank(message = "Role required")
    @Size(max = 10, message = "Role cannot be longer than 10 characters.")
    private String role;

    @NotBlank(message = "First name required")
    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name required")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
