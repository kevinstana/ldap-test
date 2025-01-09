package gr.hua.it21774.context;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.exceptions.RoleMappingException;
import gr.hua.it21774.userdetails.AppUserDetails;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LdapUserDetailsContextMapper implements UserDetailsContextMapper {

    private static final Map<String, ERole> TITLE_ROLE_MAP;

    static {
        Map<String, ERole> map = new HashMap<>();
        map.put("Προπτυχιακός Φοιτητής", ERole.STUDENT);
        map.put("Αναπληρωτής Καθηγητής", ERole.PROFESSOR);
        map.put("Επίκουρος Καθηγητής", ERole.PROFESSOR);
        map.put("Καθηγητής", ERole.PROFESSOR);
        map.put("Εργαστηριακό Διδακτικό Προσωπικό", ERole.PROFESSOR);
        map.put("Ειδικό Διδακτικό Προσωπικό", ERole.PROFESSOR);
        map.put("Ειδικό Τεχνικό Εργαστηριακό Προσωπικό", ERole.SECRETARY);
        TITLE_ROLE_MAP = Collections.unmodifiableMap(map);
    }

    @Override
    public AppUserDetails mapUserFromContext(DirContextOperations ctx, String username,
            Collection<? extends GrantedAuthority> authorities) {
        String email = ctx.getStringAttribute("email");

        ERole role = mapTitleToRole(ctx.getStringAttribute("title"), username);
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

    private ERole mapTitleToRole(String title, String username) {

        if (TITLE_ROLE_MAP.containsKey(title)) {
            return TITLE_ROLE_MAP.get(title);
        }

        if (title.equals("Λέκτορας") && username.equals("michalak")) {
            return ERole.PROFESSOR;
        }

        if (title.equals("Συνεργάτης")) {
            switch (username) {
                case "daneli":
                    return ERole.SECRETARY;
                case "violos":
                case "pmichael":
                    return ERole.PROFESSOR;
            }
        }

        if (title.equals("Διοικητικό Προσωπικό") && username.equals("fmine")) {
            return ERole.SECRETARY;
        }

        throw new RoleMappingException();
    }
}
