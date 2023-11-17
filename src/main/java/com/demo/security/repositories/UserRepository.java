package com.demo.security.repositories;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.demo.security.entities.UserEntity;

import java.util.Optional; 

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> { 
    Optional<UserEntity> findByUsername(String username); 
}
