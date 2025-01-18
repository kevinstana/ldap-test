package gr.hua.it21774.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.dto.UserListDTO;
import gr.hua.it21774.service.UserService;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestParam(defaultValue = "10") int page, @RequestParam(defaultValue = "0") int size) {

        Page<UserListDTO> users = userService.getPagedUsers(page, size);
        
        return ResponseEntity.ok().body(users);
    }
}
