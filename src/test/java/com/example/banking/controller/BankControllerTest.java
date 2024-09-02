package com.example.banking.controller;

import com.example.banking.model.rest.CreateAccountRequest;
import com.example.banking.service.BankService;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Locale;
import java.util.UUID;

public class BankControllerTest extends GlobalSpringContext {

    @MockBean
    private BankService bankService;

    private final String API_URL = "/api/account";

    private static Faker faker;

    @BeforeAll
    static void initializeFaker() {
        faker = new Faker(Locale.ENGLISH);
    }


    @Test
    void shouldSaveAccountWhenValidData() throws Exception {
        // mock
        CreateAccountRequest request = new CreateAccountRequest(faker.name().fullName(), new BigDecimal(faker.commerce().price()));
        String accountNumber = UUID.randomUUID().toString();
        // given
        BDDMockito.given(bankService.createAccount(ArgumentMatchers.any(String.class), ArgumentMatchers.any(BigDecimal.class))).willReturn(accountNumber);
        // when, verify & assertions
        MvcResult mvcResult = mockMvc.perform(
                        MockMvcRequestBuilders.post(API_URL + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
        String actualAccountNumber = mvcResult.getResponse().getContentAsString();
        Assertions.assertThat(actualAccountNumber).isNotNull();
        Assertions.assertThat(actualAccountNumber).isNotBlank();
        Assertions.assertThat(actualAccountNumber).isEqualTo(accountNumber);
    }

    @Test
    void shouldThrowAnExceptionWhenInvalidData() throws Exception {
        // mock
        CreateAccountRequest request = new CreateAccountRequest("", null);
        // when, verify & assertions
        mockMvc.perform(
                        MockMvcRequestBuilders.post(API_URL + "/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request))
                ).andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(ResponseBodyMatchers.responseBody().containsError("accountHolderName", "must not be blank"))
                .andExpect(ResponseBodyMatchers.responseBody().containsError("initialBalance", "must not be null"))
                .andReturn();
    }
}
