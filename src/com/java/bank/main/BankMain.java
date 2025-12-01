package com.java.bank.main;

import com.java.bank.model.Account;
import com.java.bank.service.BankService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class BankMain {
  private static final Scanner sc = new Scanner(System.in);
  private static final BankService bankService = new BankService();
  public static void main(String[] args) {
    System.out.println("=== Banking System Simulator ===");
    boolean running = true;

    while (running) {
      printMainMenu();
      int choice = readInt("Enter choice: ");
      try {
        switch (choice) {
          case 1 -> createAccount();
          case 2 -> performAccountOperations();
          case 3 -> searchAccounts();
          case 4 -> {
            System.out.println("Exiting... Goodbye!");
            running = false;
          }
          default -> System.out.println("Invalid choice. Try again.");
        }
      } catch (RuntimeException ex) {
        System.out.println("Error: " + ex.getMessage());
      }
    }
  }

  private static void printMainMenu() {
    System.out.println("\n1. Create an account");
    System.out.println("2. Perform operations on existing account");
    System.out.println("3. Search accounts by name");
    System.out.println("4. Exit");
  }

  private static void createAccount() {
    System.out.print("Enter full name: ");
    String name = sc.nextLine().trim();
    Account a = bankService.createAccount(name);
    System.out.println("Account created successfully. Account Number: " + a.getAccountNumber());
  }

  private static void performAccountOperations() {
    System.out.print("Enter account number: ");
    String accNo = sc.nextLine().trim();
    bankService.getAccount(accNo);

    boolean back = false;
    while (!back) {
      printAccountMenu();
      int op = readInt("Choose operation: ");
      try {
        switch (op) {
          case 1 -> doDeposit(accNo);
          case 2 -> doWithdraw(accNo);
          case 3 -> doTransfer(accNo);
          case 4 -> showBalance(accNo);
          case 5 -> back = true;
          default -> System.out.println("Invalid option.");
        }
      } catch (RuntimeException e) {
        System.out.println("Operation failed: " + e.getMessage());
      }
    }
  }

  private static void printAccountMenu() {
    System.out.println("\n1. Deposit");
    System.out.println("2. Withdraw");
    System.out.println("3. Transfer");
    System.out.println("4. Show balance");
    System.out.println("5. Return to main menu");
  }

  private static void doDeposit(String accNo) {
    BigDecimal amount = readBigDecimal("Enter deposit amount: ");
    bankService.deposit(accNo, amount);
    System.out.println("Deposit successful.");
  }

  private static void doWithdraw(String accNo) {
    BigDecimal amount = readBigDecimal("Enter withdrawal amount: ");
    bankService.withdraw(accNo, amount);
    System.out.println("Withdrawal successful.");
  }

  private static void doTransfer(String fromAcc) {
    System.out.print("Enter destination account number: ");
    String toAcc = sc.nextLine().trim();
    BigDecimal amount = readBigDecimal("Enter transfer amount: ");
    bankService.transfer(fromAcc, toAcc, amount);
    System.out.println("Transfer successful.");
  }

  private static void showBalance(String accNo) {
    Account a = bankService.getAccount(accNo);
    System.out.println("Account Number: " + a.getAccountNumber());
    System.out.println("Holder Name   : " + a.getAccountHolderName());
    System.out.println("Current Balance: " + a.getBalance());
  }

  private static void searchAccounts() {
    System.out.print("Enter name part to search: ");
    String q = sc.nextLine().trim();
    List<Account> results = bankService.searchByName(q);
    if (results.isEmpty()) {
      System.out.println("No accounts found.");
    } else {
      System.out.println("Found accounts:");
      results.forEach(acc -> System.out.println(acc));
    }
  }

  // helpers
  private static int readInt(String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        return Integer.parseInt(line);
      } catch (NumberFormatException e) {
        System.out.println("Please enter a valid integer.");
      }
    }
  }

  private static BigDecimal readBigDecimal(String prompt) {
    while (true) {
      try {
        System.out.print(prompt);
        String line = sc.nextLine().trim();
        return new BigDecimal(line).setScale(2, BigDecimal.ROUND_HALF_EVEN);
      } catch (Exception e) {
        System.out.println("Please enter a valid amount (e.g. 1000.00).");
      }
    }
  }
}
