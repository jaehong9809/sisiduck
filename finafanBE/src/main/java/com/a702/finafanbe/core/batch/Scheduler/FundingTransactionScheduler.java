package com.a702.finafanbe.core.batch.Scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class FundingTransactionScheduler {

    private final JobLauncher jobLauncher;

    private final Job transactionJob;

    @Scheduled(cron = "0 10 14 * * *")
    public void runTransactionJob() {
        try {
            log.info("*** Batch Scheduling funding transaction : {} ***", LocalDateTime.now());
            jobLauncher.run(transactionJob, new JobParametersBuilder()
                    .addDate("date", new Date())
                    .toJobParameters());
        } catch(Exception e) {
            log.error("배치 작업 실패 : {}", e.getMessage());
        }
    }

}
