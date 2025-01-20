package gr.hua.it21774.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.CommonUserDTO;
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

    @GetMapping("/external-users")
    public ResponseEntity<?> getAllExternalUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) Boolean enabled) {

        if (page == null || page < 0) {
            page = 0;
        }

        if (size == null || size < 1) {
            size = 10;
        }

        List<ERole> validRoles = new ArrayList<>();

        if (roles != null) {
            for (String role : roles) {
                try {
                    validRoles.add(ERole.valueOf(role.toUpperCase()));
                } catch (IllegalArgumentException e) {
                }
            }
        }

        List<ERole> rolesToQuery = validRoles.isEmpty() ? null : validRoles;
            
        Page<CommonUserDTO> externalUsers = externalUserService.getPagedUsers(page, size, rolesToQuery, enabled);

        return ResponseEntity.ok().body(externalUsers);
    }

    @PostMapping("/external-users")
    public ResponseEntity<?> createExternalUser(@Valid @RequestBody CreateExternalUserRequest request) {
        if (!request.getPassword().equals(request.getVerifyPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageRespone("Password mismatch"));
        }

        externalUserService.checkIfExists(request.getUsername(), request.getEmail());
        externalUserService.createExternalUser(request);

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
