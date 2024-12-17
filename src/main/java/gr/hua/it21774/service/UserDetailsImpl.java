package gr.hua.it21774.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gr.hua.it21774.entities.User;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {

    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private String email;

    private String firstName;

    private String lastName;

    private String title;


    public UserDetailsImpl(Long id,
                              String username,
                              String password,
                              Collection<? extends GrantedAuthority> authorities,
                              String email,
                              String firstName,
                              String lastName,
                              String title) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.title = title;
    }

    public static UserDetailsImpl build(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();

        return new UserDetailsImpl(
                user.getId(),
                user.getUsername(),
                "",
                authorities,
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getTitle()
        );

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

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        UserDetailsImpl appUser = (UserDetailsImpl) obj;
        return Objects.equals(id, appUser.id);
    }

}


