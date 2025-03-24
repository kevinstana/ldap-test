package gr.hua.it21774.controllers;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.requests.UpdateUserRequest;
import gr.hua.it21774.responses.MessageRespone;
import gr.hua.it21774.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String query) {

        if (query != null) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
        } else {
            query = "";
        }

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
        Page<CommonUserDTO> users = userService.getPagedUsers(intPage, size, rolesToQuery, enabled, query);

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/hua-users")
    public ResponseEntity<?> getAllHuaUsers(@RequestParam(required = false) String page,
            @RequestParam(required = false) String size,
            @RequestParam(required = false) List<String> roles,
            @RequestParam(required = false) Boolean enabled,
            @RequestParam(required = false) String query) {

        if (query != null) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
        } else {
            query = "";
        }

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

        Page<CommonUserDTO> users = userService.getPagedHuaUsers(intPage, size, rolesToQuery, enabled, query);

        return ResponseEntity.ok().body(users);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest request, @PathVariable Long id) {

        userService.updateUser(id, request.getIsEnabled());

        return ResponseEntity.ok().body(new MessageRespone("User updated!"));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<?> getUserProfile(@PathVariable String username) {
        CommonUserDTO profile = userService.getUserProfile(username);
        return ResponseEntity.ok().body(profile);
    }

    @GetMapping("/search-students")
    public ResponseEntity<?> searchStudents(@RequestParam(required = false) String query) {

        if (query != null) {
            query = URLDecoder.decode(query, StandardCharsets.UTF_8);
        }

        List<CommonUserDTO> results = userService.searchStudents(query);

        return ResponseEntity.ok().body(results);
    }
}
