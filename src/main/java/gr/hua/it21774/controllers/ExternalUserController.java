package gr.hua.it21774.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.requests.AdminChangePasswordRequest;
import gr.hua.it21774.requests.ChangeMyPasswordRequest;
import gr.hua.it21774.requests.CreateExternalUserRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.service.ExternalUserService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;

@RestController
public class ExternalUserController {

    private final ExternalUserService externalUserService;

    public ExternalUserController(ExternalUserService externalUserService) {
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

        externalUserService.checkIfExists(request.getUsername(), request.getEmail());
        externalUserService.createExternalUser(request, request.getPassword(), role);

        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageRespone("User created successfully!"));
    }

    @PostMapping("/external-users/me")
    public ResponseEntity<?> changeMyExternalUserPassword(@Valid @RequestBody ChangeMyPasswordRequest request) {
        if (!request.isPasswordMatch()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Password mismatch."));
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Claims accessTokenClaims = (Claims) authentication.getDetails();

        String username = accessTokenClaims.get("username", String.class);
        externalUserService.handleMyPasswordChange(username, request.getCurrentPassword(), request.getNewPassword());

        return ResponseEntity.ok().body(new MessageRespone("Password updated successfully!"));
    }

    @PostMapping("/external-users/{username}/change-password")
    public ResponseEntity<?> changeExternalUserPassword(@Valid @RequestBody AdminChangePasswordRequest request,
            @PathVariable String username) {
        if (!request.isPasswordMatch()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Password mismatch."));
        }

        externalUserService.changePasswordAsAdmin(username, request.getNewPassword());

        return ResponseEntity.ok().body(new MessageRespone("Password updated successfully!"));
    }
}
