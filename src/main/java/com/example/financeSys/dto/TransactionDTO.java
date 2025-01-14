package com.example.financeSys.dto;

import com.example.financeSys.enums.TransactionType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TransactionDTO {
    private BigDecimal amount;
    private LocalDate date;
    private TransactionType type;
    private String description;
    private String category;
}
