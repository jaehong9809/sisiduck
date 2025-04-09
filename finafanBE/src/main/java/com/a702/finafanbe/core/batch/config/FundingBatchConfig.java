package com.a702.finafanbe.core.batch.config;

import com.a702.finafanbe.core.batch.dto.TransactionRequest;
import com.a702.finafanbe.core.batch.dto.TransactionResponse;
import com.a702.finafanbe.core.batch.exception.RetryTransactionException;
import com.a702.finafanbe.core.batch.exception.SkipTransactionException;
import com.a702.finafanbe.core.batch.processor.FundingTransactionProcessor;
import com.a702.finafanbe.core.batch.reader.FundingTransactionReader;
import com.a702.finafanbe.core.batch.writer.FundingTransactionWriter;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import com.a702.finafanbe.core.funding.funding.entity.FundingPendingTransaction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class FundingBatchConfig {

    private final JobRepository jobRepository;

    private final PlatformTransactionManager transactionManager;

    private final FundingTransactionReader transactionReader;

    private final FundingTransactionProcessor transactionProcessor;

    private final FundingTransactionWriter transactionWriter;

    //private final SkippedTransactionRepository skippedTransactionRepository;

    @Bean
    public Job transactionJob() {
        return new JobBuilder("transactionJob", jobRepository)
                .start(transactionStep())
                .build();
    }

    @Bean
    public Step transactionStep(
    ) {
        return new StepBuilder("transactionStep", jobRepository)
                .<FundingPendingTransaction, TransactionResponse>chunk(10, transactionManager)
                .reader(transactionReader)
                .processor(transactionProcessor)
                .writer(transactionWriter)
                .faultTolerant()
                .retry(RetryTransactionException.class)
                .retryLimit(3)
                .skip(RetryTransactionException.class)
                .skipLimit(Integer.MAX_VALUE)
                .build();
    }
}
