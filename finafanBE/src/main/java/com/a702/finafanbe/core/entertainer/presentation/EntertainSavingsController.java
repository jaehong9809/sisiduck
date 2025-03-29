package com.a702.finafanbe.core.entertainer.presentation;

import com.a702.finafanbe.core.demanddeposit.application.InquireDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.entity.Account;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.EntertainerSavingsAccountRepository;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.EntertainerTransactionHistoriesRequest;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.request.StarTransferRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerDepositResponse;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.dto.response.InquireEntertainerAccountResponse;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.demanddeposit.entity.EntertainerSavingsAccount;
import com.a702.finafanbe.core.s3.service.S3Service;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/star")
@RequiredArgsConstructor
public class EntertainSavingsController {

    private static final String EMAIL = "lsc7134@naver.com";

    private final DemandDepositFacade demandDepositFacade;
    private final EntertainSavingsService entertainService;
    private final S3Service s3Service;
    private final InquireDemandDepositAccountService inquireDemandDepositAccountService;

    @GetMapping("/account/{savingAccountId}")
    public ResponseEntity<ResponseData<InquireEntertainerAccountResponse>> getEntertainerAccount(
        @PathVariable Long savingAccountId
    ) {
        return ResponseUtil.success(demandDepositFacade.getEntertainerAccount(
            EMAIL,
            savingAccountId
        ));
    }

    @GetMapping("/accounts")
    public ResponseEntity<ResponseData<List<InquireEntertainerAccountResponse>>> getStarAccounts(
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
    @PutMapping("/despoit")
    public ResponseEntity<ResponseData<EntertainerDepositResponse>> deposit(
            //@AuthMember User user,
            @ModelAttribute StarTransferRequest starTransferRequest
            ){
        ResponseEntity<UpdateDemandDepositAccountTransferResponse> exchange = demandDepositFacade.transferEntertainerAccount(
                starTransferRequest.depositAccountId(),
                starTransferRequest.transactionBalance()
        );
        String depositAccountNo = exchange.getBody().REC().stream()
            .map(transaction -> transaction.accountNo())
            .findFirst().get();
        String transactionAccountNo = exchange.getBody().REC().stream()
            .map(transaction -> transaction.transactionAccountNo())
            .findFirst().get();
        Account depositAccount = inquireDemandDepositAccountService.findAccountByAccountNo(depositAccountNo);
        Account withdrawalAccount = inquireDemandDepositAccountService.findAccountByAccountNo(
            transactionAccountNo);

        if(exchange.getStatusCode()== HttpStatus.OK){
            String image = s3Service.uploadImage(starTransferRequest.imageFile());
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
}
