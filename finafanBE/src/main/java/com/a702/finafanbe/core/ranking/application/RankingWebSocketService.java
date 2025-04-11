package com.a702.finafanbe.core.ranking.application;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.core.ranking.dto.response.EntertainerRankingResponse;
import com.a702.finafanbe.core.ranking.dto.response.RankingDetailResponse;
import com.a702.finafanbe.core.ranking.dto.response.UserRankingResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingWebSocketService {

    private final SimpMessagingTemplate messagingTemplate;
    private final RankingService rankingService;
    private final UserRepository userRepository;
    private final EntertainerRepository entertainerRepository;

    /**
     * 연예인 ID로 랭킹 목록에서 총액 조회
     */
    private Double getTotalAmountFromRanking(
            List<RankingService.EntertainerRankingEntry> entries,
            Long entertainerId
    ) {
        return entries.stream()
                .filter(entry -> entry.getEntertainerId().equals(entertainerId))
                .findFirst()
                .map(RankingService.EntertainerRankingEntry::getTotalAmount)
                .orElse(0.0);
    }

    public void updateAndBroadcastRanking(
            Long userId,
            Long entertainerId,
            double amount
    ) {
        // 1. 랭킹 업데이트
        rankingService.updateRanking(userId, entertainerId, amount);

        // 2. 연예인 일간 랭킹 조회 및 브로드캐스트
        broadcastEntertainerRanking("daily");

        // 3. 연예인 주간 랭킹 조회 및 브로드캐스트
        broadcastEntertainerRanking("weekly");

        // 4. 연예인 누적 랭킹 조회 및 브로드캐스트
        broadcastEntertainerRanking("total");

        // 5. 특정 연예인 적금 내 사용자 일간 랭킹 조회 및 브로드캐스트
        broadcastUserRanking(entertainerId, "daily");

        // 6. 특정 연예인 적금 내 사용자 주간 랭킹 조회 및 브로드캐스트
        broadcastUserRanking(entertainerId, "weekly");

        log.info("Updated and broadcast all rankings for entertainer: {}", entertainerId);
    }

    /**
     * 연예인 랭킹 브로드캐스트 (일간 또는 주간)
     */
    private void broadcastEntertainerRanking(String period) {
        List<RankingService.EntertainerRankingEntry> entries;

        // 일간, 주간, 또는 누적 랭킹 조회
        if ("daily".equals(period)) {
            entries = rankingService.getDailyEntertainerRanking();
        } else if ("weekly".equals(period)) {
            entries = rankingService.getWeeklyEntertainerRanking();
        } else if ("total".equals(period)) {
            entries = rankingService.getTotalEntertainerRanking();
        } else {
            log.warn("Invalid ranking period: {}", period);
            return;
        }

        // 응답 DTO 변환
        List<EntertainerRankingResponse> responses = convertToEntertainerRankingResponses(entries);

        // WebSocket으로 브로드캐스트
        messagingTemplate.convertAndSend(
                "/topic/ranking/" + period + "/entertainers",
                responses
        );
    }

    /**
     * 특정 연예인 적금 내 사용자 랭킹 브로드캐스트 (일간 또는 주간)
     */
    private void broadcastUserRanking(Long entertainerId, String period) {
        // 연예인 정보 조회
        Entertainer entertainer = entertainerRepository.findById(entertainerId).orElse(null);
        if (entertainer == null) {
            log.warn("Entertainer not found: {}", entertainerId);
            return;
        }

        List<RankingService.UserRankingEntry> userEntries;
        Double totalAmount;

        if ("daily".equals(period)) {
            userEntries = rankingService.getDailyUserRanking(entertainerId);
            totalAmount = getTotalAmountFromRanking(
                    rankingService.getDailyEntertainerRanking(),
                    entertainerId);
        } else {
            userEntries = rankingService.getWeeklyUserRanking(entertainerId);
            totalAmount = getTotalAmountFromRanking(
                    rankingService.getWeeklyEntertainerRanking(),
                    entertainerId);
        }

        // 사용자 랭킹 응답 DTO 변환
        List<UserRankingResponse> userResponses = convertToUserRankingResponses(userEntries);

        // 랭킹 상세 정보 응답 생성
        RankingDetailResponse response = new RankingDetailResponse(
                entertainerId,
                entertainer.getEntertainerName(),
                entertainer.getEntertainerProfileUrl(),
                totalAmount,
                userResponses
        );

        // WebSocket으로 브로드캐스트
        messagingTemplate.convertAndSend(
                "/topic/ranking/" + period + "/entertainers/" + entertainerId,
                response
        );
    }

    /**
     * EntertainerRankingEntry 목록을 EntertainerRankingResponse 목록으로 변환
     */
    private List<EntertainerRankingResponse> convertToEntertainerRankingResponses(
            List<RankingService.EntertainerRankingEntry> entries) {
        return entries.stream()
                .map(entry -> {
                    Entertainer entertainer = entertainerRepository.findById(entry.getEntertainerId())
                            .orElseThrow(()-> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));

                    String entertainerName = entertainer.getEntertainerName();
                    String profileUrl = entertainer.getEntertainerProfileUrl();

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

    /**
     * UserRankingEntry 목록을 UserRankingResponse 목록으로 변환
     */
    private List<UserRankingResponse> convertToUserRankingResponses(
            List<RankingService.UserRankingEntry> entries) {
        return entries.stream()
                .map(entry -> {
                    // 사용자 정보 조회
                    User user = userRepository.findById(entry.getUserId()).orElseThrow(()-> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundUser)));
                    String userName = user.getName();

                    return new UserRankingResponse(
                            entry.getRank(),
                            entry.getUserId(),
                            userName,
                            entry.getAmount()
                    );
                })
                .collect(Collectors.toList());
    }
}
