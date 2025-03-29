package com.a702.finafanbe.core.transaction.deposittransaction.application;

import com.a702.finafanbe.core.transaction.deposittransaction.entity.EntertainerSavingsTransactionDetail;
import com.a702.finafanbe.core.transaction.deposittransaction.entity.infrastructure.EntertainerSavingsTransactionDetailRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepositTransactionService {

    private final EntertainerSavingsTransactionDetailRepository entertainerSavingsTransactionDetailRepository;

    public List<EntertainerSavingsTransactionDetail> getEntertainerAccountTransactionsByAccountId(Long accountId) {
        return entertainerSavingsTransactionDetailRepository.findByDepositAccountId(accountId).orElseThrow(()-> new BadRequestException(
            ResponseData.createResponse(ErrorCode.NOT_FOUND_ACCOUNT)));
    }
}
