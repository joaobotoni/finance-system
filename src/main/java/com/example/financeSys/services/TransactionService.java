package com.example.financeSys.services;

import com.example.financeSys.entity.Transaction;
import com.example.financeSys.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    public void created(Transaction transaction){
        repository.save(transaction);
    }

}
