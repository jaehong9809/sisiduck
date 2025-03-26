package com.a702.finafanbe.global.common.financialnetwork.util;

import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
@Getter
@Slf4j
public class FinancialNetworkUtil {

    @Value("${ssafy.api-key}")
    private String apiKey;

    @Value("${ssafy.institution-code}")
    private String institutionCode;

    @Value("${ssafy.fintech-app-no}")
    private String fintechAppNo;

    private final AtomicInteger counter = new AtomicInteger(1);

    public String getInstitutionTransactionUniqueNo() {
        String dateTime = DateUtil.getTransmissionDate()+DateUtil.getTransmissionTime();
        int currentSequence = counter.getAndIncrement();
        String sixDigitCounter = String.format("%06d", currentSequence%1000000);
        log.info("HI" + dateTime+":"+sixDigitCounter);
        return dateTime+sixDigitCounter;
    }
}
