package gr.hua.it21774.dto;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
    private Boolean isEnabled;

    public UserDTO(Long id, String username, String email, String role, Boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }
}