package gr.hua.it21774.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<?> getAllUsers() {

        List<UserListDTO> users = userService.getAllUsers();
        
        return ResponseEntity.ok().body(users);
    }
}
