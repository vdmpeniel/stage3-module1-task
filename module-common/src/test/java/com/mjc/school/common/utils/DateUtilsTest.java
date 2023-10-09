package com.mjc.school.common.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;


class DateUtilsTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void localDateTimeToISO8601() throws Exception{
        String stringDate = "2018-08-29T06:12:15.156";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        formatter.withZone(ZoneId.of("UTC"));
        String result = DateUtils.LocalDateTimeToISO8601(LocalDateTime.parse(stringDate, formatter));
        assertEquals(stringDate, result);
    }

    @Test
    void ISO8601ToLocalDateTime() throws Exception{
        String stringDate = "2018-08-29T06:12:15.156";
        LocalDateTime localDateTime = DateUtils.ISO8601ToLocalDateTime(stringDate);

        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        formatter.withZone(ZoneId.of("UTC"));
        String result = localDateTime.format(formatter);
        assertEquals(stringDate, result);
    }
}