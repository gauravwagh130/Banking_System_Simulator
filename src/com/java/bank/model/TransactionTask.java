package com.java.bank.model;

import com.java.bank.service.BankService;

import java.math.BigDecimal;

public class TransactionTask implements Runnable{
  private final BankService bankService;
  private final String accNo;
  private final BigDecimal amount;
  private final boolean deposit;

  public TransactionTask(BankService bankService, String accNo, BigDecimal amount, boolean deposit) {
    this.bankService = bankService;
    this.accNo = accNo;
    this.amount = amount;
    this.deposit = deposit;
  }
  @Override
  public void run() {
    try {
      if (deposit) bankService.deposit(accNo, amount);
      else bankService.withdraw(accNo, amount);
      System.out.println(Thread.currentThread().getName() + " OK on " + accNo);
    } catch (RuntimeException e) {
      System.err.println(Thread.currentThread().getName() + " FAIL: " + e.getMessage());
    }
  }
}
