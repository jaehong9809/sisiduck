package com.a702.finafanbe.core.entertainer.presentation;

import com.a702.finafanbe.core.demanddeposit.dto.request.DepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountDepositResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.entertainer.application.EntertainSavingsService;
import com.a702.finafanbe.core.entertainer.dto.request.SelectStarRequest;
import com.a702.finafanbe.core.entertainer.dto.request.CreateStarAccountRequest;
import com.a702.finafanbe.core.entertainer.dto.response.EntertainerResponse;
import com.a702.finafanbe.core.entertainer.dto.response.StarAccountResponse;
import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.s3.service.S3Service;
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
        return ResponseUtil.success(entertainService.createEntertainerSavings(
                selectStarRequest
        ));
    }

    @PutMapping("/despoit")
    public ResponseEntity<ResponseData<Void>> deposit(
            //@AuthMember User user,
            String userEmail,
            DepositRequest depositRequest,
            String message,
            @RequestPart MultipartFile imageFile
            ){
        ResponseEntity<UpdateDemandDepositAccountDepositResponse> exchange = demandDepositFacade.depositAccount(
                userEmail,
                depositRequest
        );
        if(exchange.getStatusCode()== HttpStatus.OK){
            String image = s3Service.uploadImage(imageFile);
            entertainService.deposit(
                    userEmail,
                    depositRequest.accountNo(),
                    depositRequest.transactionBalance(),
                    exchange.getBody().REC().getTransactionUniqueNo().longValue(),
                    message,
                    image
            );
        }
        return ResponseUtil.success();
    }

//    @GetMapping("/accounts")
//    public ResponseEntity<ResponseData<List<?>>> getAccounts(
//            String userEmail
//    ){
//
//    }
}
