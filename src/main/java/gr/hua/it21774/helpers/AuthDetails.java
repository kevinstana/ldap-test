package gr.hua.it21774.helpers;

import org.springframework.security.core.Authentication;

public class AuthDetails {
    private Authentication authentication;
    private Long id;
    private boolean isExternal;

    public AuthDetails(Authentication authentication,
            Long id,
            boolean isExternal) {
        this.authentication = authentication;
        this.id = id;
        this.isExternal = isExternal;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsExternal() {
        return isExternal;
    }

    public void setIsExternal(boolean isExternal) {
        this.isExternal = isExternal;
    }

}
