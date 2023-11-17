package com.demo.security.services;

import java.util.List;
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
import com.demo.security.models.UserInfoDetails;
import com.demo.security.repositories.ResponseObject;
import com.demo.security.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository repository; 

    @Autowired
    private PasswordEncoder encoder; 

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
}
