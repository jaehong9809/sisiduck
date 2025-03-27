package com.a702.finafanbe.core.entertainer.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.request.DepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransactionHistoriesRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransferRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.*;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.request.StarDepositRequest;
import com.a702.finafanbe.core.entertainer.dto.request.StarTransferRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerDepositResponse;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.dto.response.InquireEntertainerAccountResponse;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.s3.service.S3Service;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/star")
@RequiredArgsConstructor
public class EntertainSavingsController {

    private final DemandDepositFacade demandDepositFacade;
    private final EntertainSavingsService entertainService;
    private final S3Service s3Service;

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

    //TODO 검색.

    @PostMapping("/savings")
    public ResponseEntity<ResponseData<StarAccountResponse>> createSavings(
//            @AuthMember User user,
            @RequestBody CreateStarAccountRequest selectStarRequest
    ){
        return ResponseUtil.success(demandDepositFacade.createEntertainerSavings(selectStarRequest));
    }

    /*
    * TODO 입금이도 되지만 자동이체도 해야하나?라고하면 해야하니까..
    * scheduler로 한 달 마다 이체가 되도록하면됨.
    *
    * */
    @PutMapping("/despoit")
    public ResponseEntity<ResponseData<EntertainerDepositResponse>> deposit(
            //@AuthMember User user,
            @ModelAttribute StarTransferRequest starTransferRequest
            ){
        ResponseEntity<UpdateDemandDepositAccountTransferResponse> exchange = demandDepositFacade.transferAccount(
                starTransferRequest.userEmail(),
                new TransferRequest(
                        starTransferRequest.depositAccountNo(),
                        starTransferRequest.depositTransactionSummary(),
                        starTransferRequest.transactionBalance(),
                        starTransferRequest.withdrawalAccountNo(),
                        starTransferRequest.withdrawalTransactionSummary()
                )
        );
        if(exchange.getStatusCode()== HttpStatus.OK){
            String image = s3Service.uploadImage(starTransferRequest.imageFile());
            EntertainerDepositResponse response = entertainService.deposit(
                    starTransferRequest.userEmail(),
                    starTransferRequest.depositAccountNo(),
                    starTransferRequest.withdrawalAccountNo(),
                    starTransferRequest.transactionBalance(),
                    exchange.getBody().REC().get(0).transactionUniqueNo(),
                    starTransferRequest.message(),
                    image
            );
            return ResponseUtil.success(response);
        }
        return ResponseUtil.failure(new BadRequestException(ResponseData.createResponse(ErrorCode.SYSTEM_ERROR)));
    }

    @GetMapping("/account")
    public ResponseEntity<ResponseData<InquireEntertainerAccountResponse>> getEntertainerAccount(
            @RequestParam String userEmail,
            @RequestParam String accountNo
    ) {
        return ResponseUtil.success(demandDepositFacade.getEntertainerAccount(
                userEmail,
                accountNo
        ));
    }

    @GetMapping("/transaction-histories")
    public ResponseEntity<ResponseData<InquireEntertainerHistoriesResponse>> getDemandDepositTransactionHistories(
            @RequestBody TransactionHistoriesRequest transactionHistoryListRequest
    ){
        return ResponseUtil.success(demandDepositFacade.inquireEntertainerHistories(
                transactionHistoryListRequest));
    }

//    @GetMapping("/accounts")
//    public ResponseEntity<ResponseData<List<?>>> getAccounts(
//            String userEmail
//    ){
//
//    }
}
