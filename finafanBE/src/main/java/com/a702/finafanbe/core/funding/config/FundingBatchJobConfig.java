package com.a702.finafanbe.core.funding.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Slf4j
//@Configuration
//@EnableBatchProcessing
//@RequiredArgsConstructor
//public class FundingBatchJobConfig {
//
//    @Bean
//    public Job fundingJob() {
//        return new JobBuilder("fundingJob", jobRepository)
//                .start(fundingStep)
//                .next(s)
//    }
//}
