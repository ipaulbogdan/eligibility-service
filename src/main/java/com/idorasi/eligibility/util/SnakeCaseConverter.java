package com.idorasi.eligibility.util;

public class SnakeCaseConverter {

    public static String convert(String input) {
        return input
                .replaceAll("([A-Z])(?=[A-Z])", "$1_")
                .replaceAll("([a-z])([A-Z])", "$1_$2")
                .toLowerCase();
    }
}
