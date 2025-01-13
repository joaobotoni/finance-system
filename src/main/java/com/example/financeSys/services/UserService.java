package com.example.financeSys.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.financeSys.entity.User;
import com.example.financeSys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public ResponseEntity<String> create(User user) {
        if (repository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username already exists in the database");
        }
        try {
            var hashPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(hashPassword);
            repository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
        } catch (RuntimeException e) {
            throw new RuntimeException("Database error while saving user: " + e.getMessage());
        }
    }
}
