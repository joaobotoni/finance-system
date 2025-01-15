package com.example.financeSys.controllers;

import java.util.UUID;
import com.example.financeSys.dto.TransactionDTO;
import com.example.financeSys.entity.Transaction;
import com.example.financeSys.services.TransactionService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<TransactionDTO> create(@RequestBody Transaction transaction, HttpServletRequest request) {
        var idUser = request.getAttribute("userId");
        transaction.setUserId((UUID) idUser);
        return service.created(transaction);
    }
}
