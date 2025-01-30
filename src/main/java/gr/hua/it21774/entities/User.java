package gr.hua.it21774.entities;

import java.time.Instant;

import gr.hua.it21774.userdetails.AppUserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 256, message = "Username cannot be longer than 256 characters.")
    private String username;

    @NotBlank
    @Email
    @Size(max = 256, message = "Email cannot be longer than 256 characters.")
    private String email;

    @NotBlank
    @Column(name = "first_name")
    @Size(max = 50, message = "First name cannot be longer than 50 characters.")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters.")
    private String lastName;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt;

    @Column(name = "last_modified")
    private Instant lastModified;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "is_enabled")
    private Boolean isEnabled = true;

    @NotNull
    @Column(name = "role_id")
    private Long roleId;

    public User() {
    }

    // Constructor used in LdapAuthController to create a new user in local db
    public User(Long id, AppUserDetails userDetails,
            Instant now,
            Boolean isEnabled, Long roleId) {
        this.id = id;
        this.username = userDetails.getUsername();
        this.email = userDetails.getEmail();
        this.firstName = userDetails.getFirstName();
        this.lastName = userDetails.getLastName();
        this.createdAt = now;
        this.isEnabled = isEnabled;
        this.roleId = roleId;
        this.lastModified = now;
    }

    // Not used somewhere at the moment
    public User(Long id, String username, String email, String firstName, String lastName,
            Instant createdAt, Instant lastModified, Long lastModifiedBy,
            Boolean isEnabled, Long roleId) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdAt = createdAt;
        this.lastModified = lastModified;
        this.lastModifiedBy = lastModifiedBy;
        this.isEnabled = isEnabled;
        this.roleId = roleId;
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

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getLastModified() {
        return lastModified;
    }

    public Long getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(Long lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Boolean getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
