package com.example.banking.controller;

import com.example.banking.model.exception.ValidationError;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Map;

public class ResponseBodyMatchers {
    public ResultMatcher containsError(String expectedFieldName, String expectedMessage) {
        return mvcResult -> {
            String jsonResponseAsString = mvcResult.getResponse().getContentAsString();
            ValidationError validationError = new ObjectMapper().readValue(jsonResponseAsString, ValidationError.class);
            Map<String, String> errors = validationError.getErrors();
            String errorMessage = errors.getOrDefault(expectedFieldName, null);
            Assertions.assertThat(errorMessage)
                    .withFailMessage("expecting exactly 1 error message with field name {%s} and message {%s}", expectedFieldName, expectedMessage)
                    .isNotNull()
                    .isEqualTo(expectedMessage);
        };
    }

    static ResponseBodyMatchers responseBody() {
        return new ResponseBodyMatchers();
    }
}
