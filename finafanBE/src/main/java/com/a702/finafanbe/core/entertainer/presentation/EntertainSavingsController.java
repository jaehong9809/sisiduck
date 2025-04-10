package com.a702.finafanbe.core.entertainer.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.facade.*;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.application.EntertainerSavingsSchedulerService;
import com.a702.finafanbe.core.entertainer.application.TopTransactionsService;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.request.StarTransferRequest;
import com.a702.finafanbe.core.entertainer.dto.request.TerminateSavingsRequest;
import com.a702.finafanbe.core.entertainer.dto.response.*;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.ranking.application.RankingWebSocketService;
import com.a702.finafanbe.core.s3.service.S3Service;
import com.a702.finafanbe.core.savings.application.SavingsAccountService;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/star")
@RequiredArgsConstructor
@Slf4j
public class EntertainSavingsController {

    private final DemandDepositFacade demandDepositFacade;
    private final EntertainSavingsService entertainService;
    private final S3Service s3Service;
    private final SavingsAccountService savingsAccountService;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;
    private final RankingWebSocketService rankingWebSocketService;
    private final EntertainSavingsService entertainSavingsService;
    private final TopTransactionsService topTransactionsService;
    private final EntertainerSavingsSchedulerService schedulerService;

    @GetMapping("/account/{savingAccountId}")
    public ResponseEntity<ResponseData<InquireEntertainerAccountResponse>> getEntertainerAccount(
        @PathVariable Long savingAccountId
    ) {
        return ResponseUtil.success(demandDepositFacade.getEntertainerAccount(
            savingAccountId
        ));
    }

    @GetMapping("/home")
    public ResponseEntity<ResponseData<List<HomeEntertainerAccountResponse>>> getStarAccountsForHome(
            @AuthMember User user
    ){
        return ResponseUtil.success(demandDepositFacade.findStarAccountsForHome(user.getSocialEmail()));
    }

    @GetMapping("/accounts")
    public ResponseEntity<ResponseData<EntertainerAccountsResponse>> getStarAccounts(
            @AuthMember User user
    ){
        return ResponseUtil.success(demandDepositFacade.findStarAccounts(user.getSocialEmail()));
    }

    @PostMapping("/savings")
    public ResponseEntity<ResponseData<StarAccountResponse>> createSavings(
            @AuthMember User user,
            @RequestBody CreateStarAccountRequest createStarAccountRequest
    ){
            return ResponseUtil.success(demandDepositFacade.createEntertainerSavings(user, createStarAccountRequest));
    }

    @PostMapping("/deposit")
    public ResponseEntity<ResponseData<EntertainerDepositResponse>> putBalanceWithDeposit(
            @AuthMember User user,
            @RequestPart("request") StarTransferRequest starTransferRequest,
            @RequestPart(value = "imageFile", required = false) MultipartFile imageFile
    ){
        ResponseEntity<UpdateDemandDepositAccountTransferResponse> exchange = demandDepositFacade.transferEntertainerAccount(
                starTransferRequest.depositAccountId(),
                starTransferRequest.transactionBalance()
        );
        String depositAccountNo = exchange.getBody().REC().stream()
            .map(transaction -> transaction.transactionAccountNo())
            .findFirst().get();
        String transactionAccountNo = exchange.getBody().REC().stream()
            .map(transaction -> transaction.accountNo())
            .findFirst().get();
        EntertainerSavingsAccount depositAccount = entertainSavingsService.findEntertainerAccountByAccountNo(depositAccountNo);
        Account withdrawalAccount = inquireDemandDepositAccountService.findAccountByAccountNo(
            transactionAccountNo);

        if(exchange.getStatusCode()== HttpStatus.OK){
            String image ="";
            if(imageFile!=null){
                image = s3Service.uploadImage(imageFile);
            }
            EntertainerDepositResponse response = entertainService.deposit(
                user.getSocialEmail(),
                depositAccount.getId(),
                withdrawalAccount.getAccountId(),
                depositAccount.getAmount().add(new BigDecimal(starTransferRequest.transactionBalance())),
                new BigDecimal(starTransferRequest.transactionBalance()),
                exchange.getBody().REC().get(1).transactionUniqueNo(),
                starTransferRequest.message(),
                image
            );

            EntertainerSavingsAccount savingsAccount = entertainSavingsService.findEntertainerAccountById(
                    starTransferRequest.depositAccountId()
            );

            entertainSavingsService.updateAccount(savingsAccount, starTransferRequest.transactionBalance());

            rankingWebSocketService.updateAndBroadcastRanking(
                    savingsAccount.getUserId(),
                    savingsAccount.getEntertainerId(),
                    starTransferRequest.transactionBalance()
            );

            return ResponseUtil.success(response);
        }
        return ResponseUtil.failure(new BadRequestException(ResponseData.createResponse(ErrorCode.SYSTEM_ERROR)));
    }


    @GetMapping("/transaction-histories/{savingAccountId}")
    public ResponseEntity<ResponseData<InquireEntertainerHistoriesResponse>> getDemandDepositTransactionHistories(
            @AuthMember User user,
            @PathVariable Long savingAccountId
    ){
        return ResponseUtil.success(demandDepositFacade.inquireEntertainerHistories(
                user,
                savingAccountId));
    }

    @PostMapping("/select")
    public ResponseEntity<ResponseData<EntertainerResponse>> selectStar(
        @AuthMember User user,
        @RequestBody SelectStarRequest selectStarRequest
    ){
        return ResponseUtil.success(entertainService.choiceStar(
            user.getSocialEmail(),
            selectStarRequest
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<Entertainer>>> getStars(){
        return ResponseUtil.success(entertainService.findStars());
    }

    @GetMapping("/favorite")
    public ResponseEntity<ResponseData<List<EntertainerResponse>>> getFavoriteEntertainers(@AuthMember User user) {
        return ResponseUtil.success(demandDepositFacade.getPossessionEntertainer(user.getSocialEmail()));
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData<List<EntertainerSearchResponse>>> searchEntertainers(
        @RequestParam(required = false) String keyword
    ) {
        return ResponseUtil.success(entertainService.searchEntertainers(keyword));
    }

    @GetMapping("/withdrawal-accounts")
    public ResponseEntity<ResponseData<List<WithdrawalAccountResponse>>> getWithdrawalAccounts(
        @AuthMember User user
    ) {
        return ResponseUtil.success(savingsAccountService.getWithdrawalAccounts(user.getSocialEmail()));
    }

    @PostMapping("/alias/{savingAccountId}")
    public ResponseEntity<ResponseData<InquireEntertainerAccountResponse>> updateAccountAlias(
            @PathVariable Long savingAccountId,
            @RequestBody  UpdateSavingsRequest updateSavingsRequest
    ){
        return ResponseUtil.success(entertainSavingsService.putAccountAlias(
                savingAccountId,
                updateSavingsRequest.newName()
        ));
    }

    @DeleteMapping("/{savingAccountId}")
    public ResponseEntity<ResponseData<Void>> deleteAccount(@PathVariable Long savingAccountId, @AuthMember User user){
        demandDepositFacade.deleteStarAccount(user, savingAccountId);
        return ResponseUtil.success();
    }

    @DeleteMapping("/{depositAccountId}/withdrawal-connection")
    public ResponseEntity<ResponseData<Void>> disconnectWithdrawalAccount(@PathVariable Long depositAccountId) {
        demandDepositFacade.deleteStarWithdrawalAccount(depositAccountId);
        return ResponseUtil.success();
    }

    @GetMapping("/{entertainerId}/top-transactions")
    public ResponseEntity<ResponseData<TopTransactionsResponse>> getTopTransactions(
        @PathVariable Long entertainerId,
        @RequestParam(defaultValue = "daily") String period) {

        log.info("Getting top transactions for entertainer: {}, period: {}",
            entertainerId, period);

        TopTransactionsResponse response =
            topTransactionsService.getTopTransactions(entertainerId, period);

        return ResponseUtil.success(response);
    }

    @PostMapping("/terminate/{savingsAccountId}")
    public ResponseEntity<ResponseData<Void>> terminateSavingsAccount(
            @AuthMember User user,
            @PathVariable Long savingsAccountId,
            @RequestBody(required = false) TerminateSavingsRequest request) {

        EntertainerSavingsAccount account = entertainSavingsService.findEntertainerAccountById(savingsAccountId);

        if (!account.getUserId().equals(user.getUserId())) {
            throw new BadRequestException(ResponseData.createResponse(ErrorCode.DATA_FORBIDDEN_ACCESS));
        }

        String reason = (request != null && request.terminationReason() != null)
                ? request.terminationReason()
                : "사용자 요청에 의한 중도해지";

        log.info("연예인 적금 중도해지 요청: 계좌ID={}, 사용자ID={}, 사유={}",
                savingsAccountId, user.getUserId(), reason);

        schedulerService.processEarlyTermination(savingsAccountId, reason);

        return ResponseUtil.success();
    }
}
