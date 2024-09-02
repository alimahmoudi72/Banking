package com.example.banking.service;

import com.example.banking.model.persistence.BankAccount;
import com.example.banking.repository.BankAccountRepository;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class BankServiceTest {

    @Mock
    private BankAccountRepository accountRepository;
    @InjectMocks
    private BankService bankService;

    private static Faker faker;

    @BeforeAll
    static void initializeFaker() {
        faker = new Faker(Locale.ENGLISH);
    }

    @Test
    void shouldReturnAccountByValidAccountNumber() {
        // mock
        BankAccount account = populateRandomBankAccount();
        // given
        BDDMockito.given(accountRepository.findByAccountNumber(ArgumentMatchers.anyString())).willReturn(Optional.of(account));
        // when
        BankAccount retrievedAccount = bankService.getAccount("12345");
        // then or assertions
        Assertions.assertThat(retrievedAccount).isNotNull();
        Assertions.assertThat(retrievedAccount.getId()).isEqualTo(account.getId());
        Assertions.assertThat(retrievedAccount.getAccountNumber()).isEqualTo(account.getAccountNumber());
        Assertions.assertThat(retrievedAccount.getAccountHolderName()).isEqualTo(account.getAccountHolderName());
        Assertions.assertThat(retrievedAccount.getBalance()).isEqualTo(account.getBalance());
    }

    private BankAccount populateRandomBankAccount() {
        return new BankAccount(faker.number().randomNumber(), UUID.randomUUID().toString(), faker.name().fullName(), new BigDecimal(faker.commerce().price()));
    }

}
