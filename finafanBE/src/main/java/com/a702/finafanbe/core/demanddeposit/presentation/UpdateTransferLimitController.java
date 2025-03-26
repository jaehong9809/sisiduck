package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.UpdateTransferLimitService;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateAccountTransferLimitRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferLimitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class UpdateTransferLimitController {

    private final UpdateTransferLimitService updateTransferLimitService;

    @PutMapping("/transfer-limit")
    public ResponseEntity<UpdateDemandDepositAccountTransferLimitResponse> modifyTransferLimit(
        @RequestBody UpdateAccountTransferLimitRequest updateDemandDepositAccountTransferLimitRequest
    ){
        return updateTransferLimitService.updateLimit(
            "/demandDeposit/updateTransferLimit",
            updateDemandDepositAccountTransferLimitRequest
        );
    }

}
