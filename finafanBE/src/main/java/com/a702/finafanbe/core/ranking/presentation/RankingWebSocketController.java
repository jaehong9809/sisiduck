package com.a702.finafanbe.core.ranking.presentation;

import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.core.ranking.application.RankingWebSocketService;
import com.a702.finafanbe.core.ranking.dto.response.EntertainerRankingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * WebSocket 메시징 핸들러
 * STOMP 프로토콜을 통한 실시간 랭킹 업데이트
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class RankingWebSocketController {

    private final RankingWebSocketService rankingWebSocketService;
    private final RankingService rankingService;

    /**
     * 클라이언트로부터 실시간 랭킹 요청 처리
     * 요청 경로: /app/ranking/daily
     * 응답 경로: /topic/ranking/daily/entertainers
     */
    @MessageMapping("/ranking/daily")
    @SendTo("/topic/ranking/daily/entertainers")
    public List<EntertainerRankingResponse> getDailyRanking() {
        log.info("WebSocket 요청: 일간 연예인 랭킹 조회");
        return RankingConverterUtil.convertToEntertainerRankingResponses(
                rankingService.getDailyEntertainerRanking());
    }

    /**
     * 클라이언트로부터 실시간 랭킹 요청 처리
     * 요청 경로: /app/ranking/weekly
     * 응답 경로: /topic/ranking/weekly/entertainers
     */
    @MessageMapping("/ranking/weekly")
    @SendTo("/topic/ranking/weekly/entertainers")
    public List<EntertainerRankingResponse> getWeeklyRanking() {
        log.info("WebSocket 요청: 주간 연예인 랭킹 조회");
        return RankingConverterUtil.convertToEntertainerRankingResponses(
                rankingService.getWeeklyEntertainerRanking());
    }
}
