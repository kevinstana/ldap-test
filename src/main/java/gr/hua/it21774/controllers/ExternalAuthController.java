package gr.hua.it21774.controllers;

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

import gr.hua.it21774.exceptions.GenericException;
import gr.hua.it21774.helpers.AuthDetails;
import gr.hua.it21774.requests.LoginRequest;
import gr.hua.it21774.responses.AuthResponse;
import gr.hua.it21774.responses.Tokens;
import gr.hua.it21774.service.JwtService;
import gr.hua.it21774.userdetails.AppUserDetails;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class ExternalAuthController {
        private final AuthenticationManager externalAuthenticationManager;
        private final JwtService jwtUtils;

        public ExternalAuthController(
                        @Qualifier(value = "external-auth") AuthenticationManager externalAuthenticationManager,
                        JwtService jwtUtils) {
                this.externalAuthenticationManager = externalAuthenticationManager;
                this.jwtUtils = jwtUtils;
        }

        @PostMapping("/login-external")
        public ResponseEntity<?> externalAuth(@Valid @RequestBody LoginRequest loginRequest) {

                Authentication authentication = externalAuthenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                                                loginRequest.getPassword()));

                SecurityContextHolder.getContext().setAuthentication(authentication);

                AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
                if (!userDetails.getIsEnabled()) {
                        throw new GenericException(HttpStatus.FORBIDDEN,
                                        "Account disabled. Please contact your system administrator.");
                }

                AuthDetails authDetails = new AuthDetails(authentication, userDetails.getId(), true);
                Tokens tokens = jwtUtils.generateTokens(authDetails);
                                AuthResponse authResponse = new AuthResponse(authDetails.getId(), userDetails.getUsername(),
                                userDetails.getEmail(), userDetails.getFirstName(), userDetails.getLastName(),
                                userDetails.getRole().name(), tokens.getAccessToken(), tokens.getRefreshToken());

                return ResponseEntity.ok().body(authResponse);
        }
}
