package com.example.financeSys.entity;

import com.example.financeSys.enums.TransactionType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    private User user;
    private Double value;
    private LocalDate date;
    private TransactionType type;
    private String description;
    private String category;

}
