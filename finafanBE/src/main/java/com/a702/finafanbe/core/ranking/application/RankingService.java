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

    private String getDailyEntertainerRankingKey() {
        LocalDate today = LocalDate.now();
        return String.format("daily:entertainer:ranking:%s", today.format(DATE_FORMATTER));
    }

    private String getWeeklyEntertainerRankingKey() {
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return String.format("weekly:entertainer:ranking:%s", monday.format(DATE_FORMATTER));
    }

    private String getDailyUserRankingKey(Long entertainerId) {
        LocalDate today = LocalDate.now();
        return String.format("daily:user:ranking:%d:%s", entertainerId, today.format(DATE_FORMATTER));
    }

    private String getWeeklyUserRankingKey(Long entertainerId) {
        LocalDate monday = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        return String.format("weekly:user:ranking:%d:%s", entertainerId, monday.format(DATE_FORMATTER));
    }

    public void updateRanking(Long userId, Long entertainerId, double amount) {
        String dailyEntertainerKey = getDailyEntertainerRankingKey();
        redisTemplate.opsForZSet().incrementScore(dailyEntertainerKey, entertainerId.toString(), amount);

        String weeklyEntertainerKey = getWeeklyEntertainerRankingKey();
        redisTemplate.opsForZSet().incrementScore(weeklyEntertainerKey, entertainerId.toString(), amount);

        String dailyUserKey = getDailyUserRankingKey(entertainerId);
        redisTemplate.opsForZSet().incrementScore(dailyUserKey, userId.toString(), amount);

        String weeklyUserKey = getWeeklyUserRankingKey(entertainerId);
        redisTemplate.opsForZSet().incrementScore(weeklyUserKey, userId.toString(), amount);

        log.info("User {} contributed {} to entertainer {}, updated rankings", userId, amount, entertainerId);
    }

    /**
     * 연예인 일간 랭킹 조회 (전체 연예인 적금 총액 기준)
     */
    public List<EntertainerRankingEntry> getDailyEntertainerRanking() {
        return getEntertainerRanking(getDailyEntertainerRankingKey());
    }

    /**
     * 연예인 주간 랭킹 조회 (전체 연예인 적금 총액 기준)
     */
    public List<EntertainerRankingEntry> getWeeklyEntertainerRanking() {
        return getEntertainerRanking(getWeeklyEntertainerRankingKey());
    }

    /**
     * 특정 연예인 적금 내 사용자 일간 랭킹 조회
     */
    public List<UserRankingEntry> getDailyUserRanking(Long entertainerId) {
        return getUserRanking(getDailyUserRankingKey(entertainerId));
    }

    /**
     * 특정 연예인 적금 내 사용자 주간 랭킹 조회
     */
    public List<UserRankingEntry> getWeeklyUserRanking(Long entertainerId) {
        return getUserRanking(getWeeklyUserRankingKey(entertainerId));
    }

    /**
     * 연예인 랭킹 조회 (공통 로직)
     */
    private List<EntertainerRankingEntry> getEntertainerRanking(String key) {
        Set<ZSetOperations.TypedTuple<String>> rankingSet =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, RANKING_LIMIT - 1);

        List<EntertainerRankingEntry> result = new ArrayList<>();
        if (rankingSet != null) {
            int rank = 1;
            for (ZSetOperations.TypedTuple<String> tuple : rankingSet) {
                String entertainerId = tuple.getValue();
                Double totalAmount = tuple.getScore();
                if (entertainerId != null && totalAmount != null) {
                    result.add(new EntertainerRankingEntry(rank++, Long.parseLong(entertainerId), totalAmount));
                }
            }
        }
        return result;
    }

    /**
     * 사용자 랭킹 조회 (공통 로직)
     */
    private List<UserRankingEntry> getUserRanking(String key) {
        Set<ZSetOperations.TypedTuple<String>> rankingSet =
                redisTemplate.opsForZSet().reverseRangeWithScores(key, 0, RANKING_LIMIT - 1);

        List<UserRankingEntry> result = new ArrayList<>();
        if (rankingSet != null) {
            int rank = 1;
            for (ZSetOperations.TypedTuple<String> tuple : rankingSet) {
                String userId = tuple.getValue();
                Double amount = tuple.getScore();
                if (userId != null && amount != null) {
                    result.add(new UserRankingEntry(rank++, Long.parseLong(userId), amount));
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

        // 연예인 일간 랭킹 키 만료 설정
        String entertainerKey = String.format("daily:entertainer:ranking:%s", yesterday.format(DATE_FORMATTER));
        redisTemplate.expire(entertainerKey, 48 * 60 * 60, java.util.concurrent.TimeUnit.SECONDS);

        // 사용자 일간 랭킹 키 만료 설정 (모든 연예인)
        String userKeyPattern = String.format("daily:user:ranking:*:%s", yesterday.format(DATE_FORMATTER));
        Set<String> keys = redisTemplate.keys(userKeyPattern);
        if (keys != null) {
            for (String k : keys) {
                redisTemplate.expire(k, 48 * 60 * 60, java.util.concurrent.TimeUnit.SECONDS);
            }
        }

        log.info("Set expiry for daily ranking keys");
    }

    /**
     * 매주 월요일 자정에 지난 주 랭킹 키 만료 설정 (2주 후 삭제)
     */
    @Scheduled(cron = "0 0 0 * * 1")
    public void setWeeklyRankingExpiry() {
        LocalDate lastMonday = LocalDate.now().minusWeeks(1).with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        // 연예인 주간 랭킹 키 만료 설정
        String entertainerKey = String.format("weekly:entertainer:ranking:%s", lastMonday.format(DATE_FORMATTER));
        redisTemplate.expire(entertainerKey, 14 * 24 * 60 * 60, java.util.concurrent.TimeUnit.SECONDS);

        // 사용자 주간 랭킹 키 만료 설정 (모든 연예인)
        String userKeyPattern = String.format("weekly:user:ranking:*:%s", lastMonday.format(DATE_FORMATTER));
        Set<String> keys = redisTemplate.keys(userKeyPattern);
        if (keys != null) {
            for (String k : keys) {
                redisTemplate.expire(k, 14 * 24 * 60 * 60, java.util.concurrent.TimeUnit.SECONDS);
            }
        }

        log.info("Set expiry for weekly ranking keys");
    }

    /**
     * 연예인 랭킹 항목 클래스
     */
    public static class EntertainerRankingEntry {
        private final int rank;
        private final Long entertainerId;
        private final Double totalAmount;

        public EntertainerRankingEntry(int rank, Long entertainerId, Double totalAmount) {
            this.rank = rank;
            this.entertainerId = entertainerId;
            this.totalAmount = totalAmount;
        }

        public int getRank() {
            return rank;
        }

        public Long getEntertainerId() {
            return entertainerId;
        }

        public Double getTotalAmount() {
            return totalAmount;
        }
    }

    /**
     * 사용자 랭킹 항목 클래스
     */
    public static class UserRankingEntry {
        private final int rank;
        private final Long userId;
        private final Double amount;

        public UserRankingEntry(int rank, Long userId, Double amount) {
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