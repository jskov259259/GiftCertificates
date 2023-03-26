package ru.clevertec.ecl.model;

public class DurationDayParser {

    public static Long parse(String value) {
        String[] values = value.split(" ");
        return Long.parseLong(values[0]);
    }
}
