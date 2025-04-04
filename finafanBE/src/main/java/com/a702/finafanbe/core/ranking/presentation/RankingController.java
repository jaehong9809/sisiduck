package com.a702.finafanbe.core.ranking.presentation;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.core.ranking.dto.response.*;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.response.ResponseData;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {

    private final RankingService rankingService;
    private final EntertainerRepository entertainerRepository;

    /**
     * 연예인 일간 랭킹 조회 (적금 총액 기준)
     */
    @GetMapping("/daily/entertainers")
    public ResponseEntity<ResponseData<List<EntertainerRankingResponse>>> getDailyEntertainerRanking() {
        log.info("Getting daily entertainer ranking");

        List<RankingService.EntertainerRankingEntry> entries = rankingService.getDailyEntertainerRanking();
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        return ResponseUtil.success(responses);
    }

    /**
     * 연예인 주간 랭킹 조회 (적금 총액 기준)
     */
    @GetMapping("/weekly/entertainers")
    public ResponseEntity<ResponseData<List<EntertainerRankingResponse>>> getWeeklyEntertainerRanking() {
        log.info("Getting weekly entertainer ranking");

        List<RankingService.EntertainerRankingEntry> entries = rankingService.getWeeklyEntertainerRanking();
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        return ResponseUtil.success(responses);
    }

    @GetMapping("/total/top3")
    public ResponseEntity<ResponseData<List<EntertainerRankingResponse>>> getTopThreeTotalRanking() {
        log.info("Getting top 3 total entertainer ranking");

        // 상위 3명의 누적 랭킹 조회
        List<RankingService.EntertainerRankingEntry> entries = rankingService.getTopNTotalEntertainerRanking(3);
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        return ResponseUtil.success(responses);
    }

    /**
     * 모든 연예인 랭킹 조회 (일간)
     */
    @GetMapping("/daily/all-entertainers")
    public ResponseEntity<ResponseData<List<EntertainerRankingResponse>>> getAllEntertainerDailyRanking() {
        log.info("Getting all entertainer daily ranking");

        // 모든 연예인 조회
        List<Entertainer> allEntertainers = entertainerRepository.findAll();

        // 모든 연예인 랭킹 조회
        List<RankingService.EntertainerRankingEntry> entries =
                rankingService.getAllEntertainerDailyRanking(allEntertainers);

        // 응답 변환
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        return ResponseUtil.success(responses);
    }

    /**
     * 모든 연예인 랭킹 조회 (주간)
     */
    @GetMapping("/weekly/all-entertainers")
    public ResponseEntity<ResponseData<List<EntertainerRankingResponse>>> getAllEntertainerWeeklyRanking() {
        log.info("Getting all entertainer weekly ranking");

        // 모든 연예인 조회
        List<Entertainer> allEntertainers = entertainerRepository.findAll();

        // 모든 연예인 랭킹 조회
        List<RankingService.EntertainerRankingEntry> entries =
                rankingService.getAllEntertainerWeeklyRanking(allEntertainers);

        // 응답 변환
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        return ResponseUtil.success(responses);
    }

    /**
     * 연예인 누적 랭킹 조회 API (전체 기간 전체 랭킹)
     * 전체 기간 동안의 누적 금액 기준 모든 연예인 랭킹 조회
     */
    @GetMapping("/total/entertainers")
    public ResponseEntity<ResponseData<List<EntertainerRankingResponse>>> getTotalRanking() {
        log.info("Getting total entertainer ranking");

        List<RankingService.EntertainerRankingEntry> entries = rankingService.getTotalEntertainerRanking();
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        return ResponseUtil.success(responses);
    }

    /**
     * EntertainerRankingEntry 목록을 EntertainerRankingResponse 목록으로 변환
     */
    private List<EntertainerRankingResponse> convertToEntertainerRankingResponses(
            List<RankingService.EntertainerRankingEntry> entries) {
        return entries.stream()
                .map(entry -> {
                    // 연예인 정보 조회
                    Entertainer entertainer = entertainerRepository.findById(entry.getEntertainerId())
                            .orElse(null);

                    String entertainerName = entertainer != null ? entertainer.getEntertainerName() : "Unknown";
                    String profileUrl = entertainer != null ? entertainer.getEntertainerProfileUrl() : "";

                    return new EntertainerRankingResponse(
                            entry.getRank(),
                            entry.getEntertainerId(),
                            entertainerName,
                            profileUrl,
                            entry.getTotalAmount()
                    );
                })
                .collect(Collectors.toList());
    }

}
