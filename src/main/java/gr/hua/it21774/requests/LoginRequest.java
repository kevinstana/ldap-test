package gr.hua.it21774.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Username required")
    @Pattern(regexp = "^\\S*$", message = "Username cannot have whitespace")
    @Size(max = 256, message = "Username cannot be more than 256 characters long")
    private String username;

    @NotBlank(message = "Password required")
    @Pattern(regexp = "^\\S*$", message = "Password cannot have whitespace")
    @Size(max = 256, message = "Password cannot be more than 256 characters long")
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

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
}
