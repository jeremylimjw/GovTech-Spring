package com.govtech.app.config;

import java.math.BigDecimal;
import java.util.List;

import com.govtech.app.model.User;
import com.govtech.app.repository.UserRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class UserConfig {
    
    // Data initiation code on startup
    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User alice = new User("Alice", new BigDecimal(1000));
            User bob = new User("Bob", new BigDecimal(2000));

            userRepository.saveAll(List.of(alice, bob));
        };
    }
}
