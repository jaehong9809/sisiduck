package com.a702.finafanbe.global.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final String TRANSMISSION_DATE = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    private static final String TRANSMISSION_TIME = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));

    private DateUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String getTransmissionDate() {
        log.info("Transmission date being sent: {}", TRANSMISSION_DATE);
        return TRANSMISSION_DATE;
    }

    public static String getTransmissionTime() {
        return TRANSMISSION_TIME;
    }

    public static String format(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.format(DATE_TIME_FORMATTER) : null;
    }

    public static String format(LocalDate date) {
        return date != null ? date.format(DATE_FORMATTER) : null;
    }
}
