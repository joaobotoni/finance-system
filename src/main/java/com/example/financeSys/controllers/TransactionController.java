package com.example.financeSys.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.financeSys.entity.Transaction;
import com.example.financeSys.dto.TransactionDTO;
import com.example.financeSys.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<TransactionDTO> create(@RequestBody Transaction transaction, HttpServletRequest request) {
        var idUser = request.getAttribute("userId");
        transaction.setUserId((UUID) idUser);
        return service.created(transaction);
    }

    @GetMapping("/transactions")
    public List<TransactionDTO> getTransactionsByList(HttpServletRequest request) {
        UUID userId = (UUID) request.getAttribute("userId");
        return service.findAllTransactions(userId);
    }
}

