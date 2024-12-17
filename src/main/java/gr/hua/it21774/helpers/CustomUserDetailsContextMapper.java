package gr.hua.it21774.helpers;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetailsContextMapper implements UserDetailsContextMapper {

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        String email = ctx.getStringAttribute("email");
        String title = ctx.getStringAttribute("title");
        String firstName = ctx.getStringAttribute("givenName");
        String lastName = ctx.getStringAttribute("sn");

        CustomUserDetails userDetails = new CustomUserDetails(
                username, "", authorities, email, title, firstName, lastName);

        return userDetails;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException("Unimplemented method 'mapUserToContext'");
    }
}
