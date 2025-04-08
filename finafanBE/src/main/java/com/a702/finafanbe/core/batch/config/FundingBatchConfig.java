package com.a702.finafanbe.core.batch.config;

import com.a702.finafanbe.core.batch.dto.TransactionRequest;
import com.a702.finafanbe.core.batch.exception.RetryableTransactionException;
import com.a702.finafanbe.core.batch.listener.SkippedTransactionListener;
import com.a702.finafanbe.core.batch.processor.FundingTransactionProcessor;
import com.a702.finafanbe.core.batch.reader.FundingTransactionReader;
import com.a702.finafanbe.core.batch.writer.FundingTransactionWriter;
import com.a702.finafanbe.core.funding.funding.entity.FundingGroup;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.RetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
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

    @Bean
    public Job transactionJob() {
        return new JobBuilder("transactionJob", jobRepository)
                .start(transactionStep())
                .build();
    }

    @Bean
    public Step transactionStep() {
        return new StepBuilder("transactionStep", jobRepository)
                .<FundingGroup, List<TransactionRequest>>chunk(10, transactionManager)
                .reader(transactionReader)
                .processor(transactionProcessor)
                .writer(transactionWriter)
                .faultTolerant()
                .retry(RetryableTransactionException.class)
                .skip()
                .listener(new SkippedTransactionListener())
                .build();
    }
}
