package com.demo.security.Database;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// import com.demo.security.entities.UserEntity;
import com.demo.security.repositories.UserRepository;

@Configuration // Chứa các Bean method
public class UserDb {
    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {
                // UserEntity user1 = new UserEntity("user1", "123", "user@gmail.com", "ROLE_USER");
                // UserEntity admin1 = new UserEntity("admin1", "123", "admin@gmail.com", "ROLE_ADMIN");

                // userRepository.save(user1);
                // userRepository.save(admin1);
            }
        };
    }
}
