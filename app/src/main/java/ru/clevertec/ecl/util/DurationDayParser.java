package ru.clevertec.ecl.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DurationDayParser {

    public Long parse(String value) {
        String[] values = value.split(" ");
        return Long.parseLong(values[0]);
    }
}
