package com.a702.finafanbe.core.batch.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;

@Slf4j
@Configuration
public class RetrySkipPolicyConfig {

    @Bean
    public SimpleRetryPolicy retryPolicy() {
        return new SimpleRetryPolicy(3);
    }

    @Bean
    public SkipPolicy skipPolicy() {
        return (throwable, skipCount) -> {
           //return throwable instanceof
        }
    }
}
