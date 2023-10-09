package com.mjc.school.common.utils;

import com.mjc.school.common.implementation.PropertyLoader;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    private final static PropertyLoader propertyLoader;

    static {
        try {
            propertyLoader = PropertyLoader.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private DateUtils(){}

    public static String LocalDateTimeToISO8601(LocalDateTime localDateTime) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        formatter.withZone(ZoneId.of("UTC"));
        return formatter.format(localDateTime);
    }

    public static LocalDateTime ISO8601ToLocalDateTime(String date) throws Exception{
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        formatter.withZone(ZoneId.of("UTC"));
        return LocalDateTime.parse(date, formatter);
    }
}
