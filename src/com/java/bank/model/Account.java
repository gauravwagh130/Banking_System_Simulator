package com.java.bank.model;

import com.java.bank.exceptions.InsufficientBalanceException;
import com.java.bank.exceptions.InvalidAmountException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Account {
  private final String accountNumber;
  private final String accountHolderName;
  private BigDecimal balance;

  public Account(String accountNumber, String holderName) {
    this.accountNumber = accountNumber;
    this.accountHolderName = holderName;
    this.balance = BigDecimal.ZERO;
  }
  public Account(String accountNumber, String accountHolderName, BigDecimal balance) {
    this.accountNumber = accountNumber;
    this.accountHolderName = accountHolderName;
    this.balance = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_EVEN);
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getAccountHolderName() {
    return accountHolderName;
  }

  public synchronized BigDecimal getBalance() {
    return balance;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }
  public synchronized void deposit(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
      throw new InvalidAmountException("Deposit amount must be > 0");
    balance = balance.add(amount).setScale(2, RoundingMode.HALF_EVEN);
  }

  public synchronized void withdraw(BigDecimal amount) {
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
      throw new InvalidAmountException("Withdrawal amount must be > 0");
    if (amount.compareTo(balance) > 0)
      throw new InsufficientBalanceException("Insufficient balance");
    balance = balance.subtract(amount).setScale(2, RoundingMode.HALF_EVEN);
  }  @Override
  public String toString() {
    return "Account{" +
      "accountNumber='" + accountNumber + '\'' +
      ", accountHolderName='" + accountHolderName + '\'' +
      ", balance=" + balance +
      '}';
  }
}
