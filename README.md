🏦 Banking System Simulator

A simple Java console application that simulates basic banking operations using Core Java, OOP Concepts, Collections, Exception Handling, Streams, and Multithreading.

📌 Overview

The Banking System Simulator allows users to:

Create a bank account

Deposit money

Withdraw money

Transfer funds between accounts

View account balance

Search accounts by name

The application follows a 3-tier architecture:

Model Layer (Account)

DAO Layer (Data Access using HashMap)

Service Layer (Business Logic)

UI / Main (Console Menu)

🧩 Features
✔ Account Creation

Enter customer name

Auto-generate account number (initials + random 4-digit number)

Initial balance = 0

✔ Deposit

Validates positive amount

Updates account balance

✔ Withdraw

Validates positive amount

Checks sufficient balance

Throws custom exceptions

✔ Transfer

Transfer funds between two accounts

Uses synchronized blocks to avoid race conditions

✔ Show Balance

Displays account number, holder name, and current balance

✔ Search Accounts

Search using partial name

Implemented using Java Streams & Lambdas

✔ Multithreading Demo

Runs multiple deposit/withdraw threads

Demonstrates concurrency safety using synchronized methods

🛠 Technologies & Concepts Used

Java 8+

OOP (Encapsulation, Abstraction)

Collections (HashMap, ConcurrentHashMap)

Exception Handling (Custom Exceptions)

Functional Programming (Streams, Lambdas)

Multithreading (Runnable, synchronized)

📂 Project Structure
src/
 └── com/java/bank/
        ├── model/
        │     └── Account.java
        ├── dao/
        │     ├── AccountDao.java
        │     └── AccountDaoImpl.java
        ├── service/
        │     └── BankService.java
        ├── utils/
        │     └── AccountNumberGenerator.java
        ├── exceptions/
        │     ├── InvalidAmountException.java
        │     ├── InvalidNameException.java
        │     ├── AccountNotFoundException.java
        │     └── InsufficientBalanceException.java
        └── Main.java

▶️ How to Run
Compile
javac -d out $(find src -name "*.java")

Run
java -cp out com.java.bank.Main

📝 Example Flow

Create account

Deposit ₹1000

Withdraw ₹200

Transfer ₹300 to another account

Show account balance

Search by name (“John”)
