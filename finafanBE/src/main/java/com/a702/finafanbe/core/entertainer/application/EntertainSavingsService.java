package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.demanddeposit.application.CreateAccountService;
import com.a702.finafanbe.core.demanddeposit.application.RetrieveAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.CreateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveProductsRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStartRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainRepository;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.a702.finafanbe.global.common.exception.ErrorCode.*;
import static com.a702.finafanbe.global.common.exception.ErrorCode.NOT_FOUND_DEMAND_DEPOSIT_PRODUCT;

@Service
@RequiredArgsConstructor
public class EntertainSavingsService {

    private final EntertainRepository entertainRepository;
    private final FinancialNetworkUtil financialNetworkUtil;
    private final EntertainerSavingsAccountRepository entertainerSavingsAccountRepository;
    private final CreateAccountService createAccountService;
    private final RetrieveAccountService retrieveAccountService;
    private final UserRepository userRepository;

    public Long createEntertainerSavings(
            User user,
            SelectStartRequest selectStartRequest
    ) {
        Long userId = findUserId(user.getSocialEmail());
        Long entertainerId = findEntertainerId(selectStartRequest.entertainer());
        String userKey = userRepository.findById(userId).orElseThrow(() ->new BadRequestException(ResponseData.createResponse(USER_NOT_FOUND))).getUserKey();

        validateNoExistingAccount(userId, entertainerId);

        return saveEntertainerSavingsAccount(
                entertainerId,
                selectStartRequest.accountName(),
                createEntertainAccount(
                        userKey,
                        findDemandDepositProductUniqueNo()
                )
        );
    }

    private Long saveEntertainerSavingsAccount(
            Long entertainerId,
            String accountName,
            String accountNo
    ) {
        return entertainerSavingsAccountRepository.save(
                EntertainerSavingsAccount.of(
                        entertainerId,
                        accountName,
                        accountNo
                )
        ).getId();
    }

    private String createEntertainAccount(String userKey, String demandDepositAccountTypeUniqueNo) {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.of(
                BaseRequestHeaderIncludeUserKey.builder()
                        .apiName("createDemandDepositAccount")
                        .apiServiceCode("createDemandDepositAccount")
                        .institutionCode(financialNetworkUtil.getInstitutionCode())
                        .fintechAppNo(financialNetworkUtil.getFintechAppNo())
                        .institutionTransactionUniqueNo(financialNetworkUtil.getInstitutionTransactionUniqueNo())
                        .apiKey(financialNetworkUtil.getApiKey())
                        .userKey(userKey)
                        .build(),
                demandDepositAccountTypeUniqueNo
        );
        String accountNo = createAccountService.createAccount(
                "/demandDeposit/createDemandDepositAccount",
                createAccountRequest
        ).getBody().REC().getAccountNo();
        return accountNo;
    }

    private String findDemandDepositProductUniqueNo() {

        return retrieveAccountService.retrieveDemandDepositList(
                "/demandDeposit/inquireDemandDepositList",
                new RetrieveProductsRequest(
                        BaseRequestHeader.create(
                                "inquireDemandDepositList",
                                financialNetworkUtil
                        )
                )
        )
                .getBody()
                .REC()
                .stream()
                .filter(rec->"dummy".equals(rec.getAccountName()))
                .findFirst()
                .map(RetrieveProductsResponse.REC::getAccountTypeUniqueNo)
                .orElseThrow(()-> new BadRequestException(
                        ResponseData.createResponse(NOT_FOUND_DEMAND_DEPOSIT_PRODUCT)
                ));
    }

    private void validateNoExistingAccount(Long userId, Long entertainerId) {
        if(existEntertainerSavings(userId, entertainerId)){
            throw new BadRequestException(ResponseData.createResponse(EXIST_SAVINGS_PRODUCT));
        }
    }

    private boolean existEntertainerSavings(Long userId, Long entertainerId) {
        return entertainerSavingsAccountRepository.findByUserIdAndEntertainerId(userId,entertainerId);
    }

    private Long findEntertainerId(String entertainer) {
        return entertainRepository.findByEntertainerName(entertainer)
                .orElseThrow(()-> new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)))
                .getEntertainerId();
    }

    private Long findUserId(String userEmail) {
        return userRepository.findBySocialEmail(userEmail)
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(NotFoundUser)))
                .getUserId();
    }

    public EntertainerResponse choiceStar(
            User user,
            String entertainerName
    ) {
        findUserId(user.getSocialEmail());
        user.updateFavoriteEntertainer(findEntertainerId(entertainerName));
        Entertainer entertainer = entertainRepository.findByEntertainerName(entertainerName).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)));
        return EntertainerResponse.of(
                entertainer.getEntertainerName(),
                entertainer.getEntertainerProfileUrl(),
                entertainer.getFandomName()
        );
    }

    public List<Entertainer> findStars() {
        return entertainRepository.findAll();
    }
}
