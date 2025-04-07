package com.a702.finafanbe.global.common.util;

import jakarta.persistence.AttributeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, LocalDateTime> {

    @Override
    public LocalDateTime convertToDatabaseColumn(LocalDateTime localDateTime) {
        return localDateTime;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(LocalDateTime dbData) {
        return dbData != null ? dbData.withNano(0) : null;
    }
}
