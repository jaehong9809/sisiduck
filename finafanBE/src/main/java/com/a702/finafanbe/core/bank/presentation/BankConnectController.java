package com.a702.finafanbe.core.bank.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.bank.dto.request.AccountConnectionRequest;
import com.a702.finafanbe.core.bank.dto.request.BankAccountsRequest;
import com.a702.finafanbe.core.bank.dto.response.BankAccountConnectionResponse;
import com.a702.finafanbe.core.bank.dto.response.BankAccountResponse;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.user.entity.User;
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

    @PostMapping
    public ResponseEntity<ResponseData<List<BankAccountResponse>>> connectBankAccounts(
        @AuthMember User user,
        @RequestBody BankAccountsRequest request
    ) {
        return ResponseUtil.success(demandDepositFacade.findUserAccountsByBanks(user, request.bankIds()));
    }

    @PostMapping("/connect")
    public ResponseEntity<ResponseData<List<BankAccountConnectionResponse>>> connectUserAccounts(
        @AuthMember User user,
        @RequestBody AccountConnectionRequest request
    ) {
        return ResponseUtil.success(demandDepositFacade.connectUserAccounts(user, request.accountNos()));
    }

}
