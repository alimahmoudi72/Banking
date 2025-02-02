

### Requirements:

1- Implement a simplified banking system that supports multiple user accounts with the following functionalities:

* Account creation with an initial balance.
* Deposit and withdrawal transactions.
* Transfer funds between accounts.
* Display account balance.

2- Create a Bank class that manages user accounts. It should provide methods for creating  accounts, performing transactions, and retrieving account information.

3- Implement a BankAccount class to represent individual user accounts. Each account should have  a unique account number, an account holder's name, and a balance.

4- Ensure thread safety in the banking system to handle concurrent transactions, considering that  multiple users may be depositing, withdrawing, or transferring funds simultaneously.

5- Implement a class TransactionLogger that logs all transactions to a file. Use the Observer design  pattern to notify the logger of new transactions. Define an interface TransactionObserver with a
method like void onTransaction(String accountNumber, String transactionType, double amount).

6- Optionally, consider implementing a simple design pattern (e.g., Decorator, Strategy) to handle  different transaction types more flexibly.

7- Implement a simple console-based user interface to interact with the banking system. Users  should be able to create accounts, perform transactions, and check balances.

### Guidelines:
* Use Spring boot and Hibernate Frameworks(prefer to use h2 db)
* Use Java's ExecutorService for managing threads.
* Apply proper synchronization mechanisms to ensure thread safety in the banking system.
* Use object-oriented design principles for creating clean and modular classes.
* Apply the Observer design pattern for the transaction logger.
* Optionally, use a design pattern to enhance the flexibility of handling different transaction types.
