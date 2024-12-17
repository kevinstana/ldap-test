package gr.hua.it21774.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import gr.hua.it21774.helpers.CustomUserDetails;

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String home(Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        
        return "Hello from root";
    }
}
