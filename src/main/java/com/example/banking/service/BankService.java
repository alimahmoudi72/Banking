package com.example.banking.service;

import com.example.banking.log.TransactionLogger;
import com.example.banking.model.exception.CustomException;
import com.example.banking.model.persistence.BankAccount;
import com.example.banking.repository.BankAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class BankService {
    private final BankAccountRepository accountRepository;
    private final TransactionLogger transactionLogger;
    private final ExecutorService executorService;
    private final Lock lock = new ReentrantLock();

    public BankService(BankAccountRepository accountRepository, TransactionLogger transactionLogger, ExecutorService executorService) {
        this.accountRepository = accountRepository;
        this.transactionLogger = transactionLogger;
        this.executorService = executorService;
    }

    private void notifyLogger(String accountNumber, String transactionType, BigDecimal amount) {
        transactionLogger.onTransaction(accountNumber, transactionType, amount);
    }

    BankAccount getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new CustomException(CustomException.ErrorCode.ACCOUNT_NOT_FOUND, new RuntimeException()));
    }

    public String createAccount(String accountHolderName, BigDecimal initialBalance) {
        BankAccount account = new BankAccount();
        account.setAccountNumber(UUID.randomUUID().toString());
        account.setAccountHolderName(accountHolderName);
        account.setBalance(initialBalance);
        return accountRepository.save(account).getAccountNumber();
    }

    public BigDecimal getBalance(String accountNumber) {
        BankAccount account = getAccount(accountNumber);
        return account.getBalance();
    }

    public void depositAsync(String accountNumber, BigDecimal amount) {
        executorService.submit(() -> deposit(accountNumber, amount));
    }

    private void deposit(String accountNumber, BigDecimal amount) {
        lock.lock();
        try {
            BankAccount account = getAccount(accountNumber);
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
            notifyLogger(accountNumber, "Deposit", amount);
        } finally {
            lock.unlock();
        }
    }

    public void withdrawAsync(String accountNumber, BigDecimal amount) {
        executorService.submit(() -> withdraw(accountNumber, amount));
    }

    private void withdraw(String accountNumber, BigDecimal amount) {
        lock.lock();
        try {
            BankAccount account = getAccount(accountNumber);
            if (account.getBalance().compareTo(amount) < 0) {
                throw new CustomException(CustomException.ErrorCode.INSUFFICIENT_FUNDS, new RuntimeException());
            }
            account.setBalance(account.getBalance().subtract(amount));
            accountRepository.save(account);
            notifyLogger(accountNumber, "Withdraw", amount);
        } finally {
            lock.unlock();
        }
    }

    public void transferAsync(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        executorService.submit(() -> transfer(fromAccountNumber, toAccountNumber, amount));
    }

    private void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        lock.lock();
        try {
            withdraw(fromAccountNumber, amount);
            deposit(toAccountNumber, amount);
            notifyLogger(fromAccountNumber, "Transfer", amount);
            notifyLogger(toAccountNumber, "Transfer", amount);
        } finally {
            lock.unlock();
        }
    }
}
