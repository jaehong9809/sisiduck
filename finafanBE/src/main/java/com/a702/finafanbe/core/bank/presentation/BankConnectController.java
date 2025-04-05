package com.a702.finafanbe.core.bank.presentation;

import com.a702.finafanbe.core.bank.dto.request.BankAccountsRequest;
import com.a702.finafanbe.core.bank.dto.response.BankAccountResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/bank/accounts")
@RequiredArgsConstructor
public class BankConnectController {

    private final DemandDepositFacade demandDepositFacade;

    @GetMapping
    public ResponseEntity<ResponseData<List<BankAccountResponse>>> connectBankAccounts(
//        @AuthMember User user,
        @RequestBody BankAccountsRequest request
    ) {
        List<BankAccountResponse> accounts = demandDepositFacade.findUserAccountsByBanks(request.bankIds());
        return ResponseUtil.success(accounts);
    }



}
