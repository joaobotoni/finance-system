package com.example.financeSys.entity;

import com.example.financeSys.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_transaction")
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "amount")
    private BigDecimal amount;

    private LocalDate date;
    private TransactionType type;
    private String description;
    private String category;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

