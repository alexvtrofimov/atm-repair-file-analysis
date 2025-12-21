package com.example.atm.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatterUtil {
    public final static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    public final static DateTimeFormatter dateExcelFormatter = DateTimeFormatter.ofPattern("M/d/yy H:m");

    public static String getDateStringFormat(LocalDateTime localDateTime) {
        return dateTimeFormatter.format(localDateTime);
    }


}
