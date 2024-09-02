# Concurrent Banking System

This is the [task](TASK.md).

## Description

* Simplicity and Readability are two factors which are specially considered in this implementation.


* Please look at http://localhost:8080/swagger-ui/index.html to find swagger documentation.


## Key Queries

* `POST /api/account/create`
  An endpoint to receive account info (account holder name and initial balance), ingest it into the system and return account number.


* `GET /api/account/balance/$accountNumber`
  An endpoint to receive account number and return the balance.


* `POST /api/account/deposit`
  An endpoint to deposit the input amount to the desired account number. Calling method will execute asynchronously.


* `POST /api/account/withdraw`
  An endpoint to withdraw the input amount from the desired account number. Calling method will execute asynchronously.


* `POST /api/account/transfer`
  An endpoint to transfer the input amount from the one account number to another one. Calling method will execute asynchronously.

