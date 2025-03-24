package com.a702.finafanbe.global.common.financialnetwork.header;

public interface RequestHeader {
    String getApiName();
    String getTransmissionDate();
    String getTransmissionTime();
    String getInstitutionCode();
    String getFintechAppNo();
    String getApiServiceCode();
    String getInstitutionTransactionUniqueNo();
    String getApiKey();
}
