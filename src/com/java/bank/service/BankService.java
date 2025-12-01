package com.java.bank.service;

import com.java.bank.dao.AccountDao;
import com.java.bank.dao.AccountDaoImpl;
import com.java.bank.exceptions.*;
import com.java.bank.model.Account;
import com.java.bank.util.AccountNumberGenerator;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BankService {

  private final AccountDao dao = new AccountDaoImpl();

  public Account createAccount(String name) {
    if (name == null || name.trim().isEmpty()) throw new InvalidNameException("Name required");

    String accNo;
    do {
      accNo = AccountNumberGenerator.generate(name);
    } while (dao.findByAccountNumber(accNo) != null);

    Account a = new Account(accNo, name.trim());
    dao.createAccount(a);
    return a;
  }

  public void deposit(String accNo, BigDecimal amount) {
    Account a = validate(accNo);
    a.deposit(amount);
    dao.updateBalance(accNo, a.getBalance());
  }

  public void withdraw(String accNo, BigDecimal amount) {
    Account a = validate(accNo);
    a.withdraw(amount);
    dao.updateBalance(accNo, a.getBalance());
  }

  public void transfer(String fromAcc, String toAcc, BigDecimal amount) {
    if (fromAcc == null || toAcc == null) throw new InvalidAmountException("Account numbers required");
    if (fromAcc.equals(toAcc)) throw new InvalidAmountException("Cannot transfer to same account");
    if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
      throw new InvalidAmountException("Amount must be > 0");

    Account a = validate(fromAcc);
    Account b = validate(toAcc);

    Account first = a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? a : b;
    Account second = first == a ? b : a;

    synchronized (first) {
      synchronized (second) {
        if (a.getBalance().compareTo(amount) < 0)
          throw new InsufficientBalanceException("Insufficient funds");
        a.withdraw(amount);
        b.deposit(amount);

        dao.updateBalance(a.getAccountNumber(), a.getBalance());
        dao.updateBalance(b.getAccountNumber(), b.getBalance());
      }
    }
  }

  public Account getAccount(String accNo) {
    return validate(accNo);
  }

  public List<Account> searchByName(String namePart) {
    if (namePart == null || namePart.trim().isEmpty()) return List.of();
    String np = namePart.toLowerCase();
    return dao.findAll().values().stream()
      .filter(ac -> ac.getAccountHolderName().toLowerCase().contains(np))
      .sorted(Comparator.comparing(Account::getAccountHolderName))
      .collect(Collectors.toList());
  }


  private Account validate(String accNo) {
    if (accNo == null || accNo.trim().isEmpty()) throw new AccountNotFoundException("Invalid account number");
    Account a = dao.findByAccountNumber(accNo);
    if (a == null) throw new AccountNotFoundException("Account not found: " + accNo);
    return a;
  }
}
