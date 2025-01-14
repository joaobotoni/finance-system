package com.example.financeSys.services;

import com.example.financeSys.entity.Transaction;
import com.example.financeSys.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public ResponseEntity created(Transaction transaction){
        try {
            repository.save(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body("Transaction: " + transaction.getId() + " created successfully");
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error creating transaction");
        }
    }
}
