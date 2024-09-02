package com.example.banking.model.exception;

public class CustomException extends RuntimeException {
    private final ErrorCode code;

    public CustomException(ErrorCode code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }

    public enum ErrorCode {
        GENERAL("Internal server error"),
        ACCOUNT_NOT_FOUND("Account not found"),
        INSUFFICIENT_FUNDS("Insufficient funds");

        private final String description;

        ErrorCode(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }
}
