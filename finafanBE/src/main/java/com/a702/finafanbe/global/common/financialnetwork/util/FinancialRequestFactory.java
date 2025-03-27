package com.a702.finafanbe.global.common.financialnetwork.util;

import com.a702.finafanbe.core.demanddeposit.dto.request.*;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FinancialRequestFactory {

    private final FinancialNetworkUtil financialNetworkUtil;
    private final UserRepository userRepository;

    public UserKeyAccountNoRequest UserKeyAccountNoRequest(
            String email,
            String accountNo,
            String apiName
    ) {
        return new UserKeyAccountNoRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                ),
                accountNo
        );
    }
    public DeleteAccountRequest deleteAccountRequest(
            String email,
            String accountNumber,
            String apiName,
            String refundAccountNo
    ){
        return new DeleteAccountRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                ),
                accountNumber,
                refundAccountNo
        );
    }
    public UserKeyRequest userKeyRequest(
            String email,
            String apiName
    ) {
        return new UserKeyRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                )
        );
    }

    public CreateAccountRequest UserKeyAccountTypeUniqueNoRequest(
            String email,
            String accountTypeUniqueNo,
            String apiName
    ) {
        return new CreateAccountRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                ),
                accountTypeUniqueNo
        );
    }

    private BaseRequestHeader createRequestHeader(String apiName){
        return BaseRequestHeader.builder()
                .apiName(apiName)
                .transmissionDate(DateUtil.getTransmissionDate())
                .transmissionTime(DateUtil.getTransmissionTime())
                .institutionCode(financialNetworkUtil.getInstitutionCode())
                .fintechAppNo(financialNetworkUtil.getFintechAppNo())
                .apiServiceCode(apiName)
                .institutionTransactionUniqueNo(financialNetworkUtil.getInstitutionTransactionUniqueNo())
                .apiKey(financialNetworkUtil.getApiKey())
                .build();
    }

    private BaseRequestHeaderIncludeUserKey createRequestHeaderWithUserKey(
            String email,
            String apiName
    ){
        String userEmail = findUser(email).getSocialEmail();
        return BaseRequestHeaderIncludeUserKey.builder()
                .apiName(apiName)
                .transmissionDate(DateUtil.getTransmissionDate())
                .transmissionTime(DateUtil.getTransmissionTime())
                .institutionCode(financialNetworkUtil.getInstitutionCode())
                .fintechAppNo(financialNetworkUtil.getFintechAppNo())
                .apiServiceCode(apiName)
                .institutionTransactionUniqueNo(financialNetworkUtil.getInstitutionTransactionUniqueNo())
                .apiKey(financialNetworkUtil.getApiKey())
                .userKey(userRepository.findBySocialEmail(userEmail).orElseThrow(()->new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundUser))).getUserKey())
                .build();
    }

    private User findUser(String userEmail) {
        return userRepository.findBySocialEmail(userEmail).orElseThrow(() -> new BadRequestException(ResponseData.builder()
                .code(ErrorCode.NotFoundUser.getCode())
                .message(ErrorCode.NotFoundUser.getMessage())
                .build()));
    }

    public AccountTransactionHistoriesRequest inquireHistories(
            String email,
            String apiName,
            String accountNo,
            String startDate,
            String endDate,
            String transactionType,
            String orderByType
    ) {
        return new AccountTransactionHistoriesRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                ),
                accountNo,
                startDate,
                endDate,
                transactionType,
                orderByType
        );
    }

    public InquireTransactionHistoryRequest inquireHistory(
            String email,
            String apiName,
            String accountNo,
            Long transactionUniqueNo
    ) {
        return new InquireTransactionHistoryRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                ),
                accountNo,
                transactionUniqueNo
        );
    }

    public UpdateAccountRequest depositAccount(
            String email,
            String apiName,
            String accountNo,
            Long transactionBalance,
            String summary
    ) {
        return new UpdateAccountRequest(
                createRequestHeaderWithUserKey(
                        email,
                        apiName
                ),
                accountNo,
                transactionBalance,
                summary
        );
    }

    public RetrieveProductsRequest inquireProducts(String apiName) {
        return new RetrieveProductsRequest(createRequestHeader(apiName));
    }
}
