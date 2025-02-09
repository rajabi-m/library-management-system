package org.example.utils;

import java.time.LocalDate;

public class ParserUtils {
    public static LocalDate parseDate(String date) {
        if (date == null)
            return null;

        if (date.isEmpty() || date.equals("null"))
            return null;

        return LocalDate.parse(date);
    }
}
