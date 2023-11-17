package com.demo.security.entities;


import lombok.AllArgsConstructor; 
import lombok.Data; 
import lombok.NoArgsConstructor; 
  
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthEntity {
    private String username; 
    private String password; 
}
