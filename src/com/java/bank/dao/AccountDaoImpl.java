package com.java.bank.dao;

import com.java.bank.model.Account;

import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AccountDaoImpl implements AccountDao {

  private final Map<String, Account> accounts = new ConcurrentHashMap<>();

  @Override
  public String createAccount(Account account) {
    accounts.put(account.getAccountNumber(), account);
    return account.getAccountNumber();
  }

  @Override
  public Account findByAccountNumber(String accNo) {
    return accounts.get(accNo);
  }

  @Override
  public boolean exists(String accNo) {
    return accounts.containsKey(accNo);
  }

  @Override
  public Map<String, Account> findAll() {
    return accounts;
  }
  @Override
  public void updateBalance(String accNo, BigDecimal newBalance) {
    Account a = accounts.get(accNo);
    if (a != null) {
      synchronized (a) {
        BigDecimal current = a.getBalance();
        BigDecimal diff = newBalance.subtract(current);
        if (diff.compareTo(BigDecimal.ZERO) > 0) a.deposit(diff);
        else if (diff.compareTo(BigDecimal.ZERO) < 0) a.withdraw(diff.abs());
      }
    }
  }
}
