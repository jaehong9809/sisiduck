package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerDepositResponse;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.entertainer.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainRepository;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.financialnetwork.header.BaseRequestHeader;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.a702.finafanbe.global.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntertainSavingsService {

    private final EntertainRepository entertainRepository;
    private final EntertainerSavingsAccountRepository entertainerSavingsAccountRepository;
    private final UserRepository userRepository;
    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;

    @Transactional
    public StarAccountResponse createEntertainerSavings(
            CreateStarAccountRequest createStartAccountRequest,
            String accountNo
    ) {
        User user = findUser(createStartAccountRequest.userEmail());
        Long entertainerId = findEntertainerId(createStartAccountRequest.entertainerId());

        validateNoExistingAccount(user.getUserId(), entertainerId);

        EntertainerSavingsAccount entertainerSavingsAccount = saveEntertainerSavingsAccount(
                user.getUserId(),
                entertainerId,
                createStartAccountRequest.accountName(),
                accountNo
        );
        return StarAccountResponse.of(
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

    private Long findEntertainerId(Long entertainerId) {
        return entertainRepository.findByEntertainerId(entertainerId)
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
        user.updateFavoriteEntertainer(findEntertainerId(selectStarRequest.entertainerId()));
        Entertainer entertainer = entertainRepository.findByEntertainerId(selectStarRequest.entertainerId()).orElseThrow(()->new BadRequestException(ResponseData.createResponse(NotFoundEntertainer)));
        return EntertainerResponse.of(
                entertainer.getEntertainerName(),
                entertainer.getEntertainerProfileUrl(),
                entertainer.getFandomName()
        );
    }

    public List<Entertainer> findStars() {
        return entertainRepository.findAll();
    }


    public EntertainerDepositResponse deposit(
            String userEmail,
            String depositAccountNo,
            String withdrawalAccountNo,
            Long transactionBalance,
            Long transactionUniqueNo,
            String message,
            String imageUrl
    ) {
        Long userId = findUser(userEmail).getUserId();
        entertainerSavingsTransactionDetailRepository.save(
                EntertainerSavingsTransactionDetail.of(
                        userId,
                        depositAccountNo,
                        withdrawalAccountNo,
                        transactionBalance,
                        transactionUniqueNo,
                        message,
                        imageUrl
                )
        );
        return EntertainerDepositResponse.of(
                depositAccountNo,
                withdrawalAccountNo,
                transactionBalance,
                transactionUniqueNo,
                message,
                imageUrl
        );
    }
}
