package com.a702.finafanbe.core.entertainer.application;

import com.a702.finafanbe.core.entertainer.entity.Entertainer;
import com.a702.finafanbe.core.entertainer.entity.infrastructure.EntertainerRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntertainerService {

    private final EntertainerRepository entertainerRepository;

    public Entertainer findEntertainerById(Long entertainerId){
        return entertainerRepository.findById(entertainerId).orElseThrow(()-> new BadRequestException(ResponseData.createResponse(ErrorCode.NotFoundEntertainer)));
    }
}
