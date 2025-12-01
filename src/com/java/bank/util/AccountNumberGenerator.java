package com.java.bank.util;

import java.util.Arrays;
import java.util.Random;

public class AccountNumberGenerator {

  private static final Random rnd = new Random();

  public static String generate(String fullName) {
    String initials = Arrays.stream(fullName.trim().split("\\s+"))
      .filter(s -> !s.isEmpty())
      .map(s -> s.substring(0,1).toUpperCase())
      .reduce("", String::concat);

    int num = 1000 + rnd.nextInt(9000);

    return initials + num;
  }
}
