package ru.home.shop.utils;

public class StringUtils extends org.springframework.util.StringUtils {

    public static String random(int size) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < size; i++) {
            sb.append('a');
        }

        return sb.toString();
    }
}
