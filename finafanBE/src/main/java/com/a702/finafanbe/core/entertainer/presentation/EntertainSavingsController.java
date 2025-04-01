package com.a702.finafanbe.core.entertainer.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.demanddeposit.facade.*;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.request.StarTransferRequest;
import com.a702.finafanbe.core.entertainer.dto.response.*;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.ranking.application.RankingWebSocketService;
import com.a702.finafanbe.core.s3.service.S3Service;
import com.a702.finafanbe.core.savings.application.SavingsAccountService;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/star")
@RequiredArgsConstructor
public class EntertainSavingsController {

    private static final String EMAIL = "lsc7134@naver.com";

    private final DemandDepositFacade demandDepositFacade;
    private final EntertainSavingsService entertainService;
    private final S3Service s3Service;
    private final SavingsAccountService savingsAccountService;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;
    private final RankingWebSocketService rankingWebSocketService;
    private final EntertainSavingsService entertainSavingsService;

    @GetMapping("/account/{savingAccountId}")
    public ResponseEntity<ResponseData<InquireEntertainerAccountResponse>> getEntertainerAccount(
        @PathVariable Long savingAccountId
    ) {
        return ResponseUtil.success(demandDepositFacade.getEntertainerAccount(
            EMAIL,
            savingAccountId
        ));
    }

    @GetMapping("/home")
    public ResponseEntity<ResponseData<List<HomeEntertainerAccountResponse>>> getStarAccountsForHome(
//            @AuthMember User user
    ){
        return ResponseUtil.success(demandDepositFacade.findStarAccountsForHome(EMAIL));
    }

    @GetMapping("/accounts")
    public ResponseEntity<ResponseData<EntertainerAccountsResponse>> getStarAccounts(
//            @AuthMember User user
    ){
        return ResponseUtil.success(demandDepositFacade.findStarAccounts(EMAIL));
    }

    @PostMapping("/savings")
    public ResponseEntity<ResponseData<StarAccountResponse>> createSavings(
//            @AuthMember User user,
            @RequestBody CreateStarAccountRequest createStarAccountRequest
    ){
        return ResponseUtil.success(demandDepositFacade.createEntertainerSavings(createStarAccountRequest));
    }

    /*
    * TODO scheduler로 한 달 마다 이체가 되도록하면됨.
    *
    * */
    @PutMapping("/deposit")
    public ResponseEntity<ResponseData<EntertainerDepositResponse>> deposit(
            //@AuthMember User user,
            @ModelAttribute StarTransferRequest starTransferRequest
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
        Account depositAccount = inquireDemandDepositAccountService.findAccountByAccountNo(depositAccountNo);
        Account withdrawalAccount = inquireDemandDepositAccountService.findAccountByAccountNo(
            transactionAccountNo);

        if(exchange.getStatusCode()== HttpStatus.OK){
            String image ="";
            if(starTransferRequest.imageFile()!=null){
                image = s3Service.uploadImage(starTransferRequest.imageFile());
            }
            EntertainerDepositResponse response = entertainService.deposit(
                EMAIL,
                depositAccount.getAccountId(),
                withdrawalAccount.getAccountId(),
                depositAccount.addAmount(new BigDecimal(starTransferRequest.transactionBalance())),
                new BigDecimal(starTransferRequest.transactionBalance()),
                exchange.getBody().REC().get(0).transactionUniqueNo(),
                starTransferRequest.message(),
                image
            );

            EntertainerSavingsAccount savingsAccount = entertainSavingsService.findEntertainerAccountByDepositAccountId(
                    starTransferRequest.depositAccountId()
            );

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
            @PathVariable Long savingAccountId
    ){
        return ResponseUtil.success(demandDepositFacade.inquireEntertainerHistories(
                savingAccountId));
    }

    @PostMapping("/select")
    public ResponseEntity<ResponseData<EntertainerResponse>> selectStar(
//            @AuthMember User user,
        @RequestBody SelectStarRequest selectStarRequest
    ){
        return ResponseUtil.success(entertainService.choiceStar(
            selectStarRequest
        ));
    }

    @GetMapping
    public ResponseEntity<ResponseData<List<Entertainer>>> getStars(){
        return ResponseUtil.success(entertainService.findStars());
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseData<List<EntertainerSearchResponse>>> searchEntertainers(
        @RequestParam(required = false) String keyword
    ) {
        return ResponseUtil.success(entertainService.searchEntertainers(keyword));
    }

    @GetMapping("/withdrawal-accounts")
    public ResponseEntity<ResponseData<List<WithdrawalAccountResponse>>> getWithdrawalAccounts(
//        @AuthMember User user;
    ) {
        String email = EMAIL;

        List<WithdrawalAccountResponse> accounts = savingsAccountService.getWithdrawalAccounts(email);
        return ResponseUtil.success(accounts);
    }

    @PutMapping("/alias/{savingAccountId}")
    public ResponseEntity<ResponseData<InquireEntertainerAccountResponse>> updateAccountAlias(
            @PathVariable Long savingAccountId,
            String newName
    ){
        return ResponseUtil.success(entertainSavingsService.putAccountAlias(
                savingAccountId,
                newName
        ));
    }

//    @DeleteMapping("/{savingAccountId}")
//    public ResponseEntity<ResponseData<Void>> deleteAccountAlias(@PathVariable Long savingAccountId){
//        return ResponseUtil.success(entertainSavingsService.del)
//    }
}
