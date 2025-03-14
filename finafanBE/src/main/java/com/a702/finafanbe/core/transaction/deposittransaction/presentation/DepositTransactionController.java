package com.a702.finafanbe.core.transaction.deposittransaction.presentation;

import com.a702.finafanbe.core.transaction.deposittransaction.application.DepositTransactionService;
import com.a702.finafanbe.core.transaction.deposittransaction.dto.response.DepositTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class DepositTransactionController {

    private final DepositTransactionService depositTransactionService;

    @GetMapping("/deposit")
    public ResponseEntity<DepositTransactionResponse> getDepositAccounts() {
        return ResponseEntity.ok(depositTransactionService.getSavingAccounts());
    }
}
