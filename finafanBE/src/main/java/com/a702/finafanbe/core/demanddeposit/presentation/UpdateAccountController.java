package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.UpdateAccountService;
import com.a702.finafanbe.core.demanddeposit.dto.request.DepositRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.TransferRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateAccountTransferRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountDepositResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferResponse;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountWithdrawalResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
public class UpdateAccountController {

    private final DemandDepositFacade demandDepositFacade;
    private final UpdateAccountService updateAccountService;

    @PutMapping("/withdrawal")
    public ResponseEntity<UpdateDemandDepositAccountWithdrawalResponse> updateDemandDepositAccountWithdrawal(
            @RequestBody UpdateAccountRequest updateDemandDepositAccountWithdrawalRequest
    ){
        return updateAccountService.withdrawal(
                "/demandDeposit/updateDemandDepositAccountWithdrawal",
                updateDemandDepositAccountWithdrawalRequest);
    }

    @PutMapping("/deposit")
    public ResponseEntity<ResponseData<UpdateDemandDepositAccountDepositResponse>> updateDemandDepositAccountDeposit(
//            @AuthMember User user,
            String userEmail,
            @RequestBody DepositRequest depositRequest
    ){
        return ResponseUtil.success(demandDepositFacade.depositAccount(
                userEmail,
                depositRequest
        ).getBody());
    }

    @PutMapping("/transfer")
    public ResponseEntity<UpdateDemandDepositAccountTransferResponse> updateDemandDepositAccountTransfer(
            String userEmail,
            @RequestBody TransferRequest transferRequest
    ){
        return demandDepositFacade.transferAccount(
                userEmail,
                transferRequest
        );
    }
}
