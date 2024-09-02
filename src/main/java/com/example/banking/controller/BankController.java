package com.example.banking.controller;

import com.example.banking.model.rest.CreateAccountRequest;
import com.example.banking.model.rest.TransactionRequest;
import com.example.banking.model.rest.TransferRequest;
import com.example.banking.service.BankService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/account")
public class BankController {
    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createAccount(@Valid @RequestBody CreateAccountRequest request) {
        return bankService.createAccount(request.getAccountHolderName(), request.getInitialBalance());
    }

    @GetMapping("/balance/{accountNumber}")
    public BigDecimal getBalance(@PathVariable("accountNumber") String accountNumber) {
        return bankService.getBalance(accountNumber);
    }

    @PostMapping("/deposit")
    public void deposit(@Valid @RequestBody TransactionRequest request) {
        bankService.depositAsync(request.getAccountNumber(), request.getAmount());
    }

    @PostMapping("/withdraw")
    public void withdraw(@Valid @RequestBody TransactionRequest request) {
        bankService.withdrawAsync(request.getAccountNumber(), request.getAmount());
    }

    @PostMapping("/transfer")
    public void transfer(@Valid @RequestBody TransferRequest request) {
        bankService.transferAsync(request.getFromAccountNumber(), request.getToAccountNumber(), request.getAmount());
    }
}
