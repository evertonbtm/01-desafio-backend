package br.com.batista.desafio01.utils;

import java.security.SecureRandom;

public class RandomicUtils {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String[] EMAIL_DOMAINS = {"gmail.com", "yahoo.com", "outlook.com", "hotmail.com"};


    public static Long generateNumeric(int length) {
        if (length <= 0) throw new IllegalArgumentException("O tamanho deve ser maior que zero.");
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(RANDOM.nextInt(10));
        }
        return Long.parseLong(builder.toString());
    }


    public static String generateAlphaNumeric(int length) {
        if (length <= 0) throw new IllegalArgumentException("O tamanho deve ser maior que zero.");
        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return builder.toString();
    }


    public static String generateRandomEmail() {
        String username = generateAlphaNumeric(8);
        String domain = EMAIL_DOMAINS[RANDOM.nextInt(EMAIL_DOMAINS.length)];
        return username + "@" + domain;
    }

}
