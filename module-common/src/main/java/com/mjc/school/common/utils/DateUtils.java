package com.mjc.school.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateUtils {


    public static String LocalDateTimeToISO8601(LocalDateTime localDateTime) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        formatter.withZone(ZoneId.of("UTC"));
        return formatter.format(localDateTime);
    }

    public static LocalDateTime ISO8601ToLocalDateTime(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        formatter.withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(date, formatter);
    }
}
