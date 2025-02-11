package org.example.utils;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class RegexUtils {
    public static final String dateRegex = "^(?<year>\\d{4})-(?<month>\\d{2})-(?<day>\\d{2})$";

    public static LocalDate parseDate(String date){
        Pattern pattern = Pattern.compile(dateRegex);
        Matcher matcher = pattern.matcher(date);
        if(matcher.find()){
            int year = Integer.parseInt(matcher.group("year"));
            int month = Integer.parseInt(matcher.group("month"));
            int day = Integer.parseInt(matcher.group("day"));

            if (year < 0 || month < 1 || month > 12 || day < 1 || day > 31) {
                return null;
            }

            return LocalDate.of(year, month, day);
        }
        return null;
    }
}
