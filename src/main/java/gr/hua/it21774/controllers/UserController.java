package gr.hua.it21774.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.CommonUserDTO;
import gr.hua.it21774.enums.ERole;
import gr.hua.it21774.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            @RequestParam(required = false) List<String> roles) {

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
        Page<CommonUserDTO> users = userService.getPagedUsers(page, size, rolesToQuery);

        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/users/hua")
    public ResponseEntity<?> getAllHuaUsers(@RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<CommonUserDTO> users = userService.getPagedHuaUsers(page, size);

        return ResponseEntity.ok().body(users);
    }
}
