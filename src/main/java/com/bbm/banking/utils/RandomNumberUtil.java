package com.bbm.banking.utils;

import java.util.Random;

public class RandomNumberUtil {

    public static String generateRandomNumber(int digits) {
        String accountNumber = "";
        int x;
        char[] stringChars = new char[digits];

        for (int i = 0; i < digits; i++) {
            Random random = new Random();
            x = random.nextInt(9);

            stringChars[i] = Integer.toString(x).toCharArray()[0];
        }

        accountNumber = new String(stringChars);
        return accountNumber.trim();
    }
}
