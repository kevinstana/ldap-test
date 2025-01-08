package gr.hua.it21774.controllers;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
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
import gr.hua.it21774.entities.User;
import gr.hua.it21774.helpers.AuthDetails;
import gr.hua.it21774.requests.LoginRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.responses.AuthResponse;
import gr.hua.it21774.respository.RoleRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.service.JwtService;
import gr.hua.it21774.userdetails.AppUserDetails;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class LdapAuthController {
        private final AuthenticationManager ldapAuthenticationManager;
        private final UserRepository userRepository;
        private final RoleRepository roleRepository;
        private final JwtService jwtUtils;

        public LdapAuthController(@Qualifier(value = "ldap-auth") AuthenticationManager ldapAuthenticationManager,
                        UserRepository userRepository,
                        JwtService jwtUtils, RoleRepository roleRepository) {
                this.ldapAuthenticationManager = ldapAuthenticationManager;
                this.userRepository = userRepository;
                this.jwtUtils = jwtUtils;
                this.roleRepository = roleRepository;
        }

        @PostMapping("/login")
        public ResponseEntity<?> ldapAuth(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = ldapAuthenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();

                EnabledUserDTO user = userRepository
                                .findIdAndIsEnabledByUsername(userDetails.getUsername().toLowerCase())
                                .orElse(new EnabledUserDTO(0L, false));

                if (user.getId().equals(0L)) {
                        Long roleId = roleRepository.findIdByRole(userDetails.getRole()).get();

                        Instant createdAt = Instant.now();
                        User newUser = new User(0L, userDetails,
                                        createdAt, true, roleId);
                        userRepository.save(newUser);
                        user.setId(newUser.getId());
                } else if (!user.getIsEnabled()) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new MessageRespone(
                                        "Account deactivated. Please contact your system administrator."));
                }

                AuthDetails authDetails = new AuthDetails(authentication, user.getId(), false);
                AuthResponse authResponse = jwtUtils.generateTokens(authDetails);

                return ResponseEntity.ok().body(authResponse);
        }
}
