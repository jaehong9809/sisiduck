package com.a702.finafanbe.global.common.financialnetwork.header;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.beans.factory.annotation.Value;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponseHeader {
    private String responseCode;
    private String responseMessage;
    private String apiName;
    private String transmissionDate;
    private String transmissionTime;
    @Value("${ssafy.api-key}")
    private String apiKey;
    private String apiServiceCode;
    private String institutionTransactionUniqueNo;
}
