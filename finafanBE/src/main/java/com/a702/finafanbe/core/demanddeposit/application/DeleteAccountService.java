package com.a702.finafanbe.core.demanddeposit.application;

import com.a702.finafanbe.core.demanddeposit.dto.request.DeleteAccountRequest;
import com.a702.finafanbe.core.demanddeposit.dto.response.DeleteAccountResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.global.common.util.ApiClientUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteAccountService {

    private final ApiClientUtil apiClientUtil;
    private final AccountRepository accountRepository;

    public DeleteAccountResponse deleteAccount(String path,
        DeleteAccountRequest deleteAccountRequest
    ) {
        return apiClientUtil.callFinancialNetwork(
            path,
            deleteAccountRequest,
            DeleteAccountResponse.class
        ).getBody();
    }

    @Transactional
    public void deleteById(Long accountId) {
        accountRepository.deleteById(accountId);
    }
}
