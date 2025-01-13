package com.example.financeSys.controllers;

import com.example.financeSys.entity.Transaction;
import com.example.financeSys.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    private TransactionService service;

    public void create(@RequestBody Transaction transaction){
        service.created(transaction);
    }
}
