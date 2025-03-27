package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.demanddeposit.application.CreateAccountService;
import com.a702.finafanbe.core.demanddeposit.application.RetrieveAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.CreateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.RetrieveProductsRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.RetrieveProductsResponse;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainRepository;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerPictureRepository;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialNetworkUtil;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeaderIncludeUserKey;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.a702.finafanbe.global.common.exception.ErrorCode.*;
import static com.a702.finafanbe.global.common.exception.ErrorCode.NOT_FOUND_DEMAND_DEPOSIT_PRODUCT;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntertainSavingsService {

    private final EntertainRepository entertainRepository;
    private final FinancialNetworkUtil financialNetworkUtil;
    private final EntertainerSavingsAccountRepository entertainerSavingsAccountRepository;
    private final CreateAccountService createAccountService;
    private final RetrieveAccountService retrieveAccountService;
    private final UserRepository userRepository;
    private final EntertainerPictureRepository entertainerPictureRepository;
    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;

    public StarAccountResponse createEntertainerSavings(
            CreateStarAccountRequest createStartAccountRequest
    ) {
        User user = findUser(createStartAccountRequest.userEmail());
        Long entertainerId = findEntertainerId(createStartAccountRequest.entertainer());
        String userKey = userRepository.findById(user.getUserId()).orElseThrow(() ->new BadRequestException(ResponseData.createResponse(USER_NOT_FOUND))).getUserKey();

        validateNoExistingAccount(user.getUserId(), entertainerId);

        EntertainerSavingsAccount entertainerSavingsAccount = saveEntertainerSavingsAccount(
                user.getUserId(),
                entertainerId,
                createStartAccountRequest.accountName(),
                createEntertainAccount(
                        userKey,
                        findDemandDepositProductUniqueNo()
                )
        );
        return new StarAccountResponse(
                entertainerSavingsAccount.getUserId(),
                entertainerSavingsAccount.getEntertainerId(),
                entertainerSavingsAccount.getAccountName(),
                entertainerSavingsAccount.getAccountNo()
        );
    }

    private EntertainerSavingsAccount saveEntertainerSavingsAccount(
            Long userId,
            Long entertainerId,
            String accountName,
            String accountNo
    ) {
        return entertainerSavingsAccountRepository.save(
                EntertainerSavingsAccount.of(
                        userId,
                        entertainerId,
                        accountName,
                        accountNo
                )
        );
    }

    private String createEntertainAccount(String userKey, String demandDepositAccountTypeUniqueNo) {
        CreateAccountRequest createAccountRequest = CreateAccountRequest.of(
                BaseRequestHeaderIncludeUserKey.builder()
                        .apiName("createDemandDepositAccount")
                        .transmissionDate(DateUtil.getTransmissionDate())
                        .transmissionTime(DateUtil.getTransmissionTime())
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
                .filter(rec -> "001-1-665c2fb533fe48".equals(rec.getAccountTypeUniqueNo()))
                .findFirst()
                .map(RetrieveProductsResponse.REC::getAccountTypeUniqueNo)
                .orElseThrow(() -> new BadRequestException(
                        ResponseData.createResponse(NOT_FOUND_DEMAND_DEPOSIT_PRODUCT)
                ));
    }

    private void validateNoExistingAccount(Long userId, Long entertainerId) {
        if(existEntertainerSavings(userId, entertainerId)){
            throw new BadRequestException(ResponseData.createResponse(EXIST_SAVINGS_PRODUCT));
        }
    }

    private boolean existEntertainerSavings(
            Long userId,
            Long entertainerId
    ) {
        return entertainerSavingsAccountRepository.existsByUserIdAndEntertainerId(
                userId,
                entertainerId
        );
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

    private User findUser(String userEmail) {
        log.info("Find user by email: " + userEmail);
        return userRepository.findBySocialEmail(userEmail)
                .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(NotFoundUser)));
    }

    public EntertainerResponse choiceStar(
            SelectStarRequest selectStarRequest
    ) {
        User user = findUser(selectStarRequest.userEmail());
        user.updateFavoriteEntertainer(findEntertainerId(selectStarRequest.entertainerName()));
        Entertainer entertainer = entertainRepository.findByEntertainerName(selectStarRequest.entertainerName()).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)));
        return EntertainerResponse.of(
                entertainer.getEntertainerName(),
                entertainer.getEntertainerProfileUrl(),
                entertainer.getFandomName()
        );
    }

    public List<Entertainer> findStars() {
        return entertainRepository.findAll();
    }


    public void deposit(
            String userEmail,
            String accountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ) {
        Long userId = findUser(userEmail).getUserId();
        entertainerSavingsTransactionDetailRepository.save(
                EntertainerSavingsTransactionDetail.of(
                        userId,
                        accountNo,
                        transactionBalance,
                        transactionUniqueNo,
                        message,
                        imageUrl
                )
        );
    }
}
