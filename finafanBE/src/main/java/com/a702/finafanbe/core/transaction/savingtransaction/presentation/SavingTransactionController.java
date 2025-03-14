package com.a702.finafanbe.core.transaction.savingtransaction.presentation;

import com.a702.finafanbe.core.transaction.savingtransaction.application.SavingTransactionService;
import com.a702.finafanbe.core.transaction.savingtransaction.dto.response.SavingTransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transaction")
@RequiredArgsConstructor
public class SavingTransactionController {

    private final SavingTransactionService savingTransactionService;

    @GetMapping("/savings")
    public ResponseEntity<SavingTransactionResponse> getSavingAccounts() {
        return ResponseEntity.ok(savingTransactionService.getSavingAccounts());
    }
}
