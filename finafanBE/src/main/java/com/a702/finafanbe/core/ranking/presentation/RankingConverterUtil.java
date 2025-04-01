package com.a702.finafanbe.core.ranking.presentation;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.core.ranking.application.RankingService;
import com.a702.finafanbe.core.ranking.dto.response.EntertainerRankingResponse;
import com.a702.finafanbe.core.ranking.dto.response.UserRankingResponse;
import com.a702.finafanbe.core.user.entity.User;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 랭킹 엔티티와 응답 DTO 간 변환을 담당하는 유틸리티 클래스
 */
@Component
@RequiredArgsConstructor
public class RankingConverterUtil {

    private static EntertainerRepository entertainerRepository;
    private static UserRepository userRepository;

    /**
     * EntertainerRankingEntry 목록을 EntertainerRankingResponse 목록으로 변환
     */
    public static List<EntertainerRankingResponse> convertToEntertainerRankingResponses(
            List<RankingService.EntertainerRankingEntry> entries) {
        return entries.stream()
                .map(entry -> {
                    // 연예인 정보 조회
                    Entertainer entertainer = entertainerRepository.findById(entry.getEntertainerId())
                            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));

                    return new EntertainerRankingResponse(
                            entry.getRank(),
                            entry.getEntertainerId(),
                            entertainer.getEntertainerName(),
                            entertainer.getEntertainerProfileUrl(),
                            entry.getTotalAmount()
                    );
                })
                .collect(Collectors.toList());
    }

    /**
     * UserRankingEntry 목록을 UserRankingResponse 목록으로 변환
     */
    public static List<UserRankingResponse> convertToUserRankingResponses(
            List<RankingService.UserRankingEntry> entries) {
        return entries.stream()
                .map(entry -> {
                    // 사용자 정보 조회
                    User user = userRepository.findById(entry.getUserId())
                            .orElseThrow(() -> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundUser)));

                    return new UserRankingResponse(
                            entry.getRank(),
                            entry.getUserId(),
                            user.getName(),
                            entry.getAmount()
                    );
                })
                .collect(Collectors.toList());
    }
}