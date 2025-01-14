package com.example.financeSys.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.financeSys.dto.UserDTO;
import com.example.financeSys.entity.User;
import com.example.financeSys.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private UserRepository repository;

    public User findByUsername(String username) { return repository.findByUsername(username); }
    public ResponseEntity<Boolean> validateCredentials(String username, String password){
        User user = findByUsername(username);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        else{
           var authPassword = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
           if(authPassword.verified){
               return ResponseEntity.status(HttpStatus.ACCEPTED).build();
           }
           else{
               return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
           }
        }
    }
}
