package gr.hua.it21774.helpers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import java.util.Collection;

public class CustomUserDetails extends User {
    private String email;
    private String title;
    private String firstName;
    private String lastName;

    public CustomUserDetails(String username, String password,
            Collection<? extends GrantedAuthority> authorities,
            String email, String title, String firstName, String lastName) {
        super(username, password, authorities);
        this.email = email;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
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

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "{\n" +
                "  Name: " + getFirstName() + " " + getLastName() + ",\n" +
                "  username: " + getUsername() + ",\n" +
                "  email: " + getEmail() + ",\n" +
                "  title: " + getTitle() + "\n" +
                "}";
    }
}
