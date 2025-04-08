package com.a702.finafanbe.core.auth.presentation.resolver;

import com.a702.finafanbe.core.auth.presentation.annotation.AuthMember;
import com.a702.finafanbe.core.auth.presentation.util.JwtUtil;
import com.a702.finafanbe.core.user.entity.infrastructure.UserRepository;
import com.a702.finafanbe.global.common.exception.BadRequestException;
import com.a702.finafanbe.global.common.exception.ErrorCode;
import com.a702.finafanbe.global.common.response.ResponseData;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthMember.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        if (request == null){
            throw new BadRequestException(ResponseData.<Void>builder()
                    .code(ErrorCode.INVALID_TOKEN_REQUEST.getCode())
                    .message(ErrorCode.INVALID_TOKEN_REQUEST.getMessage())
                    .build());
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if( authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new BadRequestException(ResponseData.<Void>builder()
                    .code(ErrorCode.INVALID_ACCESS_TOKEN.getCode())
                    .message(ErrorCode.INVALID_ACCESS_TOKEN.getMessage())
                    .build());
        }
        String accessToken = authHeader.split(" ")[1];

        if(jwtUtil.isTokenValid(accessToken)){
            String subject = jwtUtil.getSubject(accessToken);
            log.info("✅ check subject : {}", subject);

            Long userId = Long.valueOf(subject);
            return userRepository.findById(userId).orElseThrow(()->new BadRequestException(ResponseData.<Void>builder()
                    .code(ErrorCode.NotFoundUser.getCode())
                    .message(ErrorCode.NotFoundUser.getMessage())
                    .build()));
        }
        throw new BadRequestException(ResponseData.<Void>builder()
                .code(ErrorCode.INVALID_TOKEN.getCode())
                .message(ErrorCode.INVALID_TOKEN.getMessage())
                .build());
    }
}
