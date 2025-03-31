package com.a702.finafanbe.core.ranking.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RankingService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final int RANKING_LIMIT = 10;

    private String getDailyRankingKey(Long entertainerId) {
        LocalDate today = LocalDate.now();
        return String.format("daily:ranking:%d:%s", entertainerId, today.format(DATE_FORMATTER));
    }

    private String getWeeklyRankingKey(Long entertainerId) {
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return String.format("weekly:ranking:%d:%s", entertainerId, monday.format(DATE_FORMATTER));
    }

    public void updateRanking(Long userId, Long entertainerId, double amount) {
        String dailyKey = getDailyRankingKey(entertainerId);
        redisTemplate.opsForZSet().incrementScore(dailyKey, userId.toString(), amount);

        String weeklyKey = getWeeklyRankingKey(entertainerId);
        redisTemplate.opsForZSet().incrementScore(weeklyKey, userId.toString(), amount);

        log.info("User {} contributed {} to entertainer {}, updated ranking", userId, amount, entertainerId);
    }

    public List<RankingEntry> getDailyRanking(Long entertainerId) {
        return getRanking(getDailyRankingKey(entertainerId));
    }

    public List<RankingEntry> getWeeklyRanking(Long entertainerId) {
        return getRanking(getWeeklyRankingKey(entertainerId));
    }

    /**
     * 특정 키의 랭킹 조회 (공통 로직)
     */
    private List<RankingEntry> getRanking(String key) {
        // 상위 RANKING_LIMIT명의 랭킹 조회 (점수 내림차순)
        Set<ZSetOperations.TypedTuple<String>> rankingSet =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, RANKING_LIMIT - 1);

        List<RankingEntry> result = new ArrayList<>();
        if (rankingSet != null) {
            int rank = 1;
            for (ZSetOperations.TypedTuple<String> tuple : rankingSet) {
                String userId = tuple.getValue();
                Double score = tuple.getScore();
                if (userId != null && score != null) {
                    result.add(new RankingEntry(rank++, Long.parseLong(userId), score));
                }
            }
        }
        return result;
    }

    /**
     * 매일 자정에 일간 랭킹 키 만료 설정 (2일 후 삭제)
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void setDailyRankingExpiry() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String key = String.format("daily:ranking:*:%s", yesterday.format(DATE_FORMATTER));

        // 패턴 매칭으로 모든 연예인의 어제 랭킹 키 찾기
        Set<String> keys = redisTemplate.keys(key);
        if (keys != null) {
            for (String k : keys) {
                // 이틀 후에 만료되도록 설정 (48시간)
                redisTemplate.expire(k, 48 * 60 * 60, java.util.concurrent.TimeUnit.SECONDS);
                log.info("Set expiry for daily ranking key: {}", k);
            }
        }
    }

    /**
     * 매주 월요일 자정에 지난 주 랭킹 키 만료 설정 (2주 후 삭제)
     */
    @Scheduled(cron = "0 0 0 * * 1")
    public void setWeeklyRankingExpiry() {
        LocalDate lastMonday = LocalDate.now().minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        String key = String.format("weekly:ranking:*:%s", lastMonday.format(DATE_FORMATTER));

        Set<String> keys = redisTemplate.keys(key);
        if (keys != null) {
            for (String k : keys) {
                // 2주 후에 만료되도록 설정 (14일)
                redisTemplate.expire(k, 14 * 24 * 60 * 60, java.util.concurrent.TimeUnit.SECONDS);
                log.info("Set expiry for weekly ranking key: {}", k);
            }
        }
    }

    /**
     * 랭킹 항목 클래스
     */
    public static class RankingEntry {
        private final int rank;
        private final Long userId;
        private final Double amount;

        public RankingEntry(int rank, Long userId, Double amount) {
            this.rank = rank;
            this.userId = userId;
            this.amount = amount;
        }

        public int getRank() {
            return rank;
        }

        public Long getUserId() {
            return userId;
        }

        public Double getAmount() {
            return amount;
        }
    }
}