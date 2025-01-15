package com.example.financeSys.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.financeSys.dto.UserDTO;
import com.example.financeSys.entity.User;
import com.example.financeSys.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final UserRepository repository;

    public AuthService(UserRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<Boolean> validateCredentials(String username, String password) {
        User user = repository.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var authPassword = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (authPassword.verified) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    public ResponseEntity<UUID> getUserIdByUsername(String username) {
        User user = repository.findIdByUsername(username);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user.getId());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
