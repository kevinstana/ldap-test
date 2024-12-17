package gr.hua.it21774.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.config.JwtUtils;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.helpers.CustomUserDetails;
import gr.hua.it21774.requests.LoginRequest;
import gr.hua.it21774.responses.JwtResponse;
import gr.hua.it21774.respository.UserRepository;
import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    JwtUtils jwtUtils;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
            JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateAccessToken(authentication);

            CustomUserDetails appUserDetails = (CustomUserDetails) authentication.getPrincipal();

            try {
                User user = new User(0L, appUserDetails.getUsername(), appUserDetails.getEmail(),
                        appUserDetails.getTitle(),
                        appUserDetails.getFirstName(), appUserDetails.getLastName());
                userRepository.save(user);
            } catch (Exception e) {

            }

            String accessToken = jwtUtils.generateAccessToken(authentication);
            String refreshToken = jwtUtils.generateRefreshToken(authentication, accessToken);

            JwtResponse myJwt = new JwtResponse(
                    appUserDetails.getUsername(),
                    appUserDetails.getEmail(),
                    appUserDetails.getTitle(),
                    appUserDetails.getFirstName() + " " + appUserDetails.getLastName(),
                    accessToken,
                    refreshToken);

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Authorization", "Bearer " + jwt);
            responseHeaders.set("Access-Control-Expose-Headers", "Authorization");

            return ResponseEntity.ok().headers(responseHeaders).body(myJwt);

        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest()
                    .body("Invalid Credentials");
        }
    }
}
