package gr.hua.it21774.userdetails;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import gr.hua.it21774.entities.ERole;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {
    private Long id;
    private String username;
    private String password;

    private String email;
    private ERole role;
    private String firstName;
    private String lastName;
    private boolean isEnabled;

    // Constructor used in LdapUserDetailsContextMapper
    public AppUserDetails(String username, String password,
            Collection<? extends GrantedAuthority> authorities,
            String email, ERole role, String firstName, String lastName) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    // Constuctor used in AuthTokenFilter
    public AppUserDetails(Long id, String username, String password,
            String email, ERole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    // Consructor used in ExternalUserDetailsService
    public AppUserDetails(Long id, String username, String password,
            String email, ERole role, boolean isEnabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
        this.isEnabled = isEnabled;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority role = new SimpleGrantedAuthority(this.role.name());
        return List.of(role);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public ERole getRole() {
        return role;
    }

    public boolean getIsEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  Name: " + getFirstName() + " " + getLastName() + ",\n" +
                "  username: " + getUsername() + ",\n" +
                "  email: " + getEmail() + ",\n" +
                "  title: " + getRole() + "\n" +
                "}";
    }
}
