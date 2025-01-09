package gr.hua.it21774.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.EnabledUserDTO;
import gr.hua.it21774.exceptions.AccountDisabledException;
import gr.hua.it21774.helpers.AuthDetails;
import gr.hua.it21774.requests.LoginRequest;
import gr.hua.it21774.responses.AuthResponse;
import gr.hua.it21774.service.JwtService;
import gr.hua.it21774.service.LdapAuthService;
import gr.hua.it21774.userdetails.AppUserDetails;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class LdapAuthController {
        private final AuthenticationManager ldapAuthenticationManager;
        private final LdapAuthService ldapAuthService;
        private final JwtService jwtUtils;

        public LdapAuthController(@Qualifier(value = "ldap-auth") AuthenticationManager ldapAuthenticationManager,
                        LdapAuthService ldapAuthService,
                        JwtService jwtUtils) {
                this.ldapAuthenticationManager = ldapAuthenticationManager;
                this.ldapAuthService = ldapAuthService;
                this.jwtUtils = jwtUtils;
        }

        @PostMapping("/login")
        public ResponseEntity<?> ldapAuth(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = ldapAuthenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

                EnabledUserDTO user = ldapAuthService.loadEnabledUser(userDetails.getUsername().toLowerCase());

                if (user.getId().equals(0L)) {
                        Long id = ldapAuthService.ldapUserToLocal(userDetails);
                        user.setId(id);
                } else if (!user.getIsEnabled()) {
                        throw new AccountDisabledException();
                }

                AuthDetails authDetails = new AuthDetails(authentication, user.getId(), false);
                AuthResponse authResponse = jwtUtils.generateTokens(authDetails);

                return ResponseEntity.ok().body(authResponse);
        }
}
