package br.com.batista.desafio01.utils;

import java.util.regex.Pattern;

public class ValidationUtils {

    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{11}");
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{14}");


    public static boolean isValidCpfOrCnpj(String value) {
        if (value == null) return false;

        String digits = value.replaceAll("\\D", ""); // Remove caracteres não numéricos

        if (CPF_PATTERN.matcher(digits).matches()) {
            return isValidCpf(digits);
        } else if (CNPJ_PATTERN.matcher(digits).matches()) {
            return isValidCnpj(digits);
        }

        return false;
    }

    private static boolean isValidCpf(String cpf) {
        if (cpf == null) return false;

        String digits = cpf.replaceAll("\\D", "");

        if (!CPF_PATTERN.matcher(digits).matches()) return false;

        if (digits.matches("(\\d)\\1{10}")) return false; // Evita CPFs com números repetidos

        int[] weightsFirst = {10, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecond = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

        return validateDigits(digits, weightsFirst, weightsSecond);
    }

    public static boolean isValidCnpj(String cnpj) {
        if (cnpj == null) return false;

        String digits = cnpj.replaceAll("\\D", "");

        if (!CNPJ_PATTERN.matcher(cnpj).matches()) return false;

        if (digits.matches("(\\d)\\1{13}")) return false; // Evita CNPJs com números repetidos

        int[] weightsFirst = {5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int[] weightsSecond = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        return validateDigits(digits, weightsFirst, weightsSecond);
    }

    private static boolean validateDigits(String number, int[] firstWeights, int[] secondWeights) {
        int firstDigit = calculateDigit(number.substring(0, firstWeights.length), firstWeights);
        int secondDigit = calculateDigit(number.substring(0, secondWeights.length), secondWeights);

        return number.equals(number.substring(0, firstWeights.length) + firstDigit + secondDigit);
    }

    private static int calculateDigit(String base, int[] weights) {
        int sum = 0;
        for (int i = 0; i < base.length(); i++) {
            sum += (base.charAt(i) - '0') * weights[i];
        }
        int remainder = sum % 11;
        return (remainder < 2) ? 0 : (11 - remainder);
    }

}
