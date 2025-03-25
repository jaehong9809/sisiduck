package com.a702.finafanbe.global.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Slf4j
@Component
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("📌 [API 요청] 🌐 Method: {} 🛠️ URL: {} 🏠 IP: {}",
                request.getMethod(), request.getRequestURL(), formatIp(request.getRemoteAddr()));
        return true;
    }

    private String formatIp(String ip) {
        return "0:0:0:0:0:0:0:1".equals(ip) ? "localhost" : ip;  // IPv6 로컬 주소를 'localhost'로 변환
    }
}
