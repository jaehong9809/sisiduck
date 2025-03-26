package com.a702.finafanbe.global.common.financialnetwork.header;


import com.a702.finafanbe.global.common.financialnetwork.entity.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.util.DateUtil;
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
    private String transmissionDate= DateUtil.getTransmissionDate();
    private String transmissionTime= DateUtil.getTransmissionTime();
    private String institutionCode;
    private String fintechAppNo;
    private String apiServiceCode;
    private String institutionTransactionUniqueNo;
    private String apiKey;

    public static BaseRequestHeader create(
            String apiName,
            FinancialNetworkUtil financialNetworkUtil
    ){
        return new BaseRequestHeader(
                apiName,
                DateUtil.getTransmissionDate(),
                DateUtil.getTransmissionTime(),
                financialNetworkUtil.getInstitutionCode(),
                financialNetworkUtil.getFintechAppNo(),
                apiName,
                financialNetworkUtil.getInstitutionTransactionUniqueNo(),
                financialNetworkUtil.getApiKey()
        );
    }
}
