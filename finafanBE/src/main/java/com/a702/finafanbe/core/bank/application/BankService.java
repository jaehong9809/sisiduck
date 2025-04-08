package com.a702.finafanbe.core.bank.application;

import static com.a702.finafanbe.global.common.exception.ErrorCode.NOT_FOUND_BANK;

import com.a702.finafanbe.core.bank.entity.Bank;
import com.a702.finafanbe.core.bank.entity.infrastructure.BankRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankService {

    private final BankRepository bankRepository;

    @Transactional(readOnly = true)
    public Bank findBankById(Long bankId) {
        return bankRepository.findByBankId(bankId).orElseThrow(
            () -> new BadRequestException(ResponseData.createResponse(NOT_FOUND_BANK)));
    }

    @Transactional(readOnly = true)
    public Bank findBankByCode(String code) {
        return bankRepository.findByBankCode(code).orElseThrow(
            ()-> new BadRequestException(ResponseData.createResponse(NOT_FOUND_BANK)
        ));
    }

    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }
}
