package com.example.banking.log;

import java.math.BigDecimal;

public interface TransactionObserver {
    void onTransaction(String accountNumber, String transactionType, BigDecimal amount);
}
