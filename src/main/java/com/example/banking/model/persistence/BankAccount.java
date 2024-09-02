package com.example.banking.model.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "bank_account_sequence_generator"
    )
    @SequenceGenerator(
            name = "bank_account_sequence_generator",
            sequenceName = "bank_account_match_sequence",
            allocationSize = 1
    )
    @Id
    private Long id;

    private String accountNumber;
    private String accountHolderName;
    private BigDecimal balance;
}
