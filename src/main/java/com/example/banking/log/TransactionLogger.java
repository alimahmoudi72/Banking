package com.example.banking.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
@Slf4j
public class TransactionLogger implements TransactionObserver {

    @Override
    public void onTransaction(String accountNumber, String transactionType, BigDecimal amount) {
        log.info("Account: " + accountNumber + ", Transaction: " + transactionType + ", Amount: " + amount);
    }

}
