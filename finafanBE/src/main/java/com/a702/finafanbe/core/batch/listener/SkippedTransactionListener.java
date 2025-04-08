package com.a702.finafanbe.core.batch.listener;

import com.a702.finafanbe.core.batch.entity.Infrastructure.SkippedTransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ChunkListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SkippedTransactionListener implements ChunkListener {

    private final SkippedTransactionRepository skippedTransactionRepository;


}
