package com.example.financeSys.services;

import com.example.financeSys.dto.TransactionDTO;
import com.example.financeSys.entity.Transaction;
import com.example.financeSys.entity.User;
import com.example.financeSys.repository.TransactionRepository;
import com.example.financeSys.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


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

    public List<TransactionDTO> findAllTransactions(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("O userId não pode ser nulo.");
        }

        // Adicione um log para verificar o valor de userId
        System.out.println("Recebido userId: " + userId);

        List<Transaction> transactions = repository.findByUserId(userId);
        if (transactions == null || transactions.isEmpty()) {
            return Collections.emptyList();
        }

        return transactions.stream()
                .map(TransactionDTO::new)
                .collect(Collectors.toList());
    }

}

