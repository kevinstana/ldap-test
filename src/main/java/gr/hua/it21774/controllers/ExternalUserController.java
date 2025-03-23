package gr.hua.it21774.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.requests.CreateExternalUserRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.service.ExternalUserService;
import jakarta.validation.Valid;

@RestController
public class ExternalUserController {

    private final ExternalUserService externalUserService;

    public ExternalUserController(ExternalUserService externalUserService) {
        this.externalUserService = externalUserService;
    }

    @GetMapping("/external-users")
    public ResponseEntity<?> getAllExternalUsers(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) Boolean enabled) {

        List<String> validSizeValues = Arrays.asList("5", "10", "15", "20", "ALL");

        Integer intPage = 0;
        try {
            intPage = Integer.parseInt(page);
            if (intPage < 0) {
                intPage = 0;
            }
        } catch (Exception e) {
            intPage = 0;
        }

        if (size == null || !validSizeValues.contains(size)) {
            size = "15";
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

        Page<CommonUserDTO> externalUsers = externalUserService.getPagedUsers(intPage, size, rolesToQuery, enabled);

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

    @GetMapping("/external-users/{username}")
    public ResponseEntity<?> getExternalUserProfile(@PathVariable String username) {
        CommonUserDTO profile = externalUserService.getExternalUserProfile(username);
        return ResponseEntity.ok().body(profile);
    }

}
