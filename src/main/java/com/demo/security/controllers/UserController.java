package com.demo.security.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.security.entities.AuthEntity;
import com.demo.security.entities.UserEntity;
import com.demo.security.repositories.ResponseObject;
import com.demo.security.services.JwtService;
import com.demo.security.services.UserService;

@RestController
@RequestMapping(path = "/auth")
public class UserController {
    @Autowired
    private UserService userService; 
  
    @Autowired
    private JwtService jwtService; 
  
    @Autowired
    private AuthenticationManager authenticationManager; 
  
    @GetMapping("") 
    public String welcome() { 
        return "Welcome this endpoint is not secure"; 
    }
    
    @GetMapping("/all")
    public ResponseEntity<ResponseObject> getAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Get users successfully", userService.loadAll())
            );
    }
  
    @PostMapping("/add") 
    public ResponseEntity<ResponseObject> addNewUser(@RequestBody UserEntity userInfo) { 
        return userService.addUser(userInfo); 
    }
    
    @GetMapping("/public") 
    @PreAuthorize("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
    public String publicProfile() { 
        return "Welcome to Public Profile"; 
    } 
  
    @GetMapping("/user") 
    @PreAuthorize("hasAuthority('ROLE_USER')") 
    public String userProfile() { 
        return "Welcome to User Profile"; 
    } 
  
    @GetMapping("/admin") 
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") 
    public String adminProfile() { 
        return "Welcome to Admin Profile"; 
    } 
  
    @PostMapping("/generateToken") 
    public String authenticateAndGetToken(@RequestBody AuthEntity authRequest) { 
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())); 
        if (authentication.isAuthenticated()) { 
            return jwtService.generateToken(authRequest.getUsername()); 
        } else { 
            throw new UsernameNotFoundException("invalid user request!"); 
        } 
    }
}
