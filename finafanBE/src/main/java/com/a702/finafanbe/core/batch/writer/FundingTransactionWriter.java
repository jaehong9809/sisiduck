package com.a702.finafanbe.core.batch.writer;

import com.a702.finafanbe.core.batch.dto.TransactionRequest;
import com.a702.finafanbe.core.demanddeposit.application.ExternalDemandDepositApiService;
import com.a702.finafanbe.core.demanddeposit.dto.response.UpdateDemandDepositAccountTransferResponse;
import com.a702.finafanbe.core.demanddeposit.entity.infrastructure.AccountRepository;
import com.a702.finafanbe.core.demanddeposit.facade.DemandDepositFacade;
import com.a702.finafanbe.core.funding.funding.entity.FundingTransactionStatus;
import com.a702.finafanbe.core.funding.funding.entity.infrastructure.FundingPendingTransactionRepository;
import com.a702.finafanbe.global.common.financialnetwork.util.FinancialRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FundingTransactionWriter implements ItemWriter<List<TransactionRequest>> {

    private final ExternalDemandDepositApiService externalDemandDepositApiService;
    private final FinancialRequestFactory financialRequestFactory;
    private final FundingPendingTransactionRepository repository;
    private final String SUCCESS_CODE = "H0000";
    private final String NO_MONEY = "A1014";
    private final String CANT_SEND_MONEY = "A1017";
    private final FundingPendingTransactionRepository fundingPendingTransactionRepository;

    @Override
    public void write(Chunk<? extends List<TransactionRequest>> chunk) throws Exception {
        for (List<TransactionRequest> transactions : chunk) {
            for (TransactionRequest request : transactions) {
                try {
                    UpdateDemandDepositAccountTransferResponse response = externalDemandDepositApiService.DemandDepositRequestWithFactory(
                            "/demandDeposit/updateDemandDepositAccountTransfer",
                            apiName -> financialRequestFactory.transferAccount(
                                    request.userEmail(),
                                    apiName,
                                    request.depositAccountNo(),
                                    request.depositTransactionSummary(),
                                    request.transactionBalance(),
                                    request.withdrawalAccountNo(),
                                    request.withdrawalTransactionSummary()
                            ),
                            "updateDemandDepositAccountTransfer",
                            UpdateDemandDepositAccountTransferResponse.class
                    ).getBody();

                    String responseCode = response.Header().getResponseCode();
                    if (responseCode.equals(NO_MONEY) || responseCode.equals(CANT_SEND_MONEY)) {
                        log.info("잔액 부족 또는 한도 초과 계좌 : {}", request.userEmail());
                        updateTransactionStatus(request.id(), FundingTransactionStatus.SKIPPERD);
                    } else if (responseCode.equals(SUCCESS_CODE)) {
                        updateTransactionStatus(request.id(), FundingTransactionStatus.SUCCESS);
                    } else {
                        // retry 대상
                    }
                } catch (Exception e) {
                    log.error("이체 실패 : {}", request, e);
                }
            }

        }
    }

    private void updateTransactionStatus(Long id, FundingTransactionStatus status) {
        repository.findById(id)
                .ifPresent(tx -> {tx.updateStatus(status);})
    }
}
