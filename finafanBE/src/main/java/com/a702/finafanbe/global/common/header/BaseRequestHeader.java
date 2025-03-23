package com.a702.finafanbe.global.common.header;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRequestHeader implements RequestHeader {
    private String apiName;
    private String transmissionDate= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    private String transmissionTime= LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmmss"));
    private String institutionCode;
    private String fintechAppNo;
    private String apiServiceCode;
    private String institutionTransactionUniqueNo;
    private String apiKey;
}
