package com.a702.finafanbe.core.bank.presentation;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.bank.application.BankConnectService;
import com.a702.finafanbe.core.bank.dto.request.BankAccountConnectionRequest;
import com.a702.finafanbe.core.bank.dto.response.BankAccountConnectionResponse;
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

    private final BankConnectService bankConnectService;

    /**
     * 사용자의 계좌 중에서 지정된 은행의 계좌만 조회합니다.
     *
     * @param user 현재 사용자
     * @param request 은행 ID 목록이 포함된 요청
     * @return 계좌 정보 응답
     */
    @GetMapping("/connect")
    public ResponseEntity<ResponseData<List<BankAccountConnectionResponse>>> connectBankAccounts(
        @AuthMember User user,
        @RequestBody BankAccountConnectionRequest request
    ) {
        log.info("Bank account connection request received for user: {}, banks: {}", user.getUserId(), request.bankIds());
        List<BankAccountConnectionResponse> accounts = bankConnectService.findUserAccountsByBanks(user, request.bankIds());
        return ResponseUtil.success(accounts);
    }

}
