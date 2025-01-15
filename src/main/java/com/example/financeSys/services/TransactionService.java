package com.example.financeSys.services;

import com.example.financeSys.dto.TransactionDTO;
import com.example.financeSys.entity.Transaction;
import com.example.financeSys.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    private final TransactionRepository repository;

    public TransactionService(TransactionRepository repository) {
        this.repository = repository;
    }

    public ResponseEntity<TransactionDTO> created(Transaction transaction) {
        try {
            repository.save(transaction);
            TransactionDTO transactionDTO = new TransactionDTO(transaction);
            return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
