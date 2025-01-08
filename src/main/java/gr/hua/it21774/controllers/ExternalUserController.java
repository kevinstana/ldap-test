package gr.hua.it21774.controllers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.entities.ExternalUser;
import gr.hua.it21774.entities.User;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.requests.ChangePasswordRequest;
import gr.hua.it21774.requests.CreateExternalUserRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.respository.ExternalUserRepository;
import gr.hua.it21774.respository.RoleRepository;
import gr.hua.it21774.respository.UserRepository;
import gr.hua.it21774.service.ExternalUserDetailsService;
import gr.hua.it21774.service.ExternalUserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
public class ExternalUserController {

    private final UserRepository userRepository;
    private final ExternalUserRepository externalUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExternalUserService externalUserService;

    public ExternalUserController(UserRepository userRepository, ExternalUserRepository externalUserRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            ExternalUserService externalUserService) {
        this.userRepository = userRepository;
        this.externalUserRepository = externalUserRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.externalUserService = externalUserService;
    }

    @PostMapping("/external-users")
    public ResponseEntity<?> createExternalUser(@Valid @RequestBody CreateExternalUserRequest request) {

        if (!request.getPassword().equals(request.getVerifyPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Password mismatch"));
        }

        ERole role;
        try {
            role = ERole.valueOf(request.getRole().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageRespone("Role not found: " + request.getRole()));
        }
        Long roleId = roleRepository.findIdByRole(role).get();

        if (externalUserRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageRespone("Username already in use"));
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageRespone("Email already in use"));
        }

        externalUserService.createExternalUser(request, passwordEncoder.encode(request.getPassword()), roleId);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageRespone("User created successfully!"));
    }

    @PostMapping("/external-users/{id}/password-change")
    public ResponseEntity<?> changeExternalUserPassword(@Valid @RequestBody ChangePasswordRequest request,
            @PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims accessTokenClaims = (Claims) authentication.getDetails();

        Long authId = Long.valueOf(accessTokenClaims.getSubject());

        if (!authId.equals(id)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        if (!request.getNewPassword().equals(request.getVerifyNewPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Password mismatch."));
        }

        String username = accessTokenClaims.get("username", String.class);
        String currentPassword = externalUserRepository
                .findPasswordByUsername(username).orElse("");

        if (!passwordEncoder.matches(request.getCurrentPassword(), currentPassword)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Invalid current password"));
        }

        externalUserRepository.updatePasswordByUsername(username, passwordEncoder.encode(request.getNewPassword()));
        return ResponseEntity.ok().body(new MessageRespone("Password updated successfully!"));
    }
}
