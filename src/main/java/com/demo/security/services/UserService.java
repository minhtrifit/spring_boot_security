package com.demo.security.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.security.entities.UserEntity;
import com.demo.security.models.Token;
import com.demo.security.models.UserInfoDetails;
import com.demo.security.repositories.ResponseObject;
import com.demo.security.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository; 

    @Autowired
    private PasswordEncoder encoder;
    
    @Autowired
    private JwtService jwtService; 

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 

        Optional<UserEntity> userDetail = repository.findByUsername(username); 

        // Converting userDetail to UserDetails 
        return userDetail.map(UserInfoDetails::new) 
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username)); 
    }
    
    public List<UserEntity> loadAll() {
        return repository.findAll();
    }

    public ResponseEntity<ResponseObject> addUser(UserEntity user) {
        Optional<UserEntity> existUser = repository.findByUsername(user.getUsername());

        if(existUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("401", "User is already exist", user.getUsername())
            );
        }
        
        user.setPassword(encoder.encode(user.getPassword())); 
        repository.save(user); 
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("201", "Add new user successfully", user)
            );
    }

    public ResponseEntity<ResponseObject> refreshAccessToken(HttpServletRequest request) {
        Map<String, Object> claims = new HashMap<>();

        String authHeader = request.getHeader("Authorization");
        String refreshToken = null; 
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            refreshToken = authHeader.substring(7); 
            username = jwtService.extractUsername(refreshToken); 
        }
        
        if(username == "") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseObject("401", "Unauthorized ", null)
            );
        }

        Token token = new Token(jwtService.createToken(claims, username, 20000), jwtService.createToken(claims, username, 60000));

        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject("200", "Refresh token successfully", token)
            );
    }

    public ResponseEntity<ResponseObject> getUserProfile(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String token = null; 
        String username = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) { 
            token = authHeader.substring(7); 
            username = jwtService.extractUsername(token); 
        }
        
        if(username == "") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ResponseObject("401", "Unauthorized ", null)
            );
        }

        Optional<UserEntity> targetUser = repository.findByUsername(username);

        if(!targetUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("404", "User not found", null)
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("200", "Get user profile successfully", targetUser)
            );
    }
}
