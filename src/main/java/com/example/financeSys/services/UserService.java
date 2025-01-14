package com.example.financeSys.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.financeSys.dto.UserDTO;
import com.example.financeSys.entity.User;
import com.example.financeSys.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<UserDTO> create(User user) {
        if (repository.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        try {
            var hashPassword = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
            user.setPassword(hashPassword);
            repository.save(user);
            UserDTO userDTO = new UserDTO(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException("Database error while saving user: " + e.getMessage());
        }
    }

    public UserDTO findByUsername(String username) {
        var result = repository.findByUsername(username);
        return result != null ? new UserDTO(result) : null;
    }
}
