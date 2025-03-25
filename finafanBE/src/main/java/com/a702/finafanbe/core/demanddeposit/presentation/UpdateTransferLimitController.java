package com.a702.finafanbe.core.demanddeposit.presentation;

import com.a702.finafanbe.core.demanddeposit.application.UpdateDemandDepositAccountService;
import com.a702.finafanbe.core.demanddeposit.application.UpdateTransferLimitService;
import com.a702.finafanbe.core.demanddeposit.dto.request.UpdateDemandDepositAccountTransferLimitRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferLimitResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/update-transfer-limit")
@RequiredArgsConstructor
public class UpdateTransferLimitController {

    private final UpdateTransferLimitService updateTransferLimitService;

    @PostMapping("/demandDeposit/updateTransferLimit")
    public ResponseEntity<UpdateDemandDepositAccountTransferLimitResponse> modifyTransferLimit(
        @RequestBody UpdateDemandDepositAccountTransferLimitRequest updateDemandDepositAccountTransferLimitRequest
    ){
        return updateTransferLimitService.updateLimit(
            "/demandDeposit/updateTransferLimit",
            updateDemandDepositAccountTransferLimitRequest
        );
    }

}
