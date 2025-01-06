package gr.hua.it21774.context;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import gr.hua.it21774.entities.ERole;
import gr.hua.it21774.userdetails.AppUserDetails;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class LdapUserDetailsContextMapper implements UserDetailsContextMapper {

    @Override
    public AppUserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        String email = ctx.getStringAttribute("email");

        ERole role = mapTitleToRole(ctx.getStringAttribute("title"));
        String firstName = ctx.getStringAttribute("givenName");
        String lastName = ctx.getStringAttribute("sn");

        AppUserDetails ldapUserDetails = new AppUserDetails(
                username, "", authorities, email, role, firstName, lastName);

        return ldapUserDetails;
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
        throw new UnsupportedOperationException("Unimplemented method 'mapUserToContext'");
    }

    private ERole mapTitleToRole(String title) {
        switch (title) {
            case "Προπτυχιακός Φοιτητής":
                return ERole.STUDENT;
            case "PROFESSOR_TITLE":
                return ERole.PROFESSOR;
            case "SECRETARY_TITLE":
                return ERole.SECRETARY;
            default:
                throw new IllegalArgumentException("Unknown title: " + title);
        }
    }
}
