package com.example.financeSys.controllers;

import com.example.financeSys.entity.Transaction;
import com.example.financeSys.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Transaction transaction) {
        return service.created(transaction);
    }
}
