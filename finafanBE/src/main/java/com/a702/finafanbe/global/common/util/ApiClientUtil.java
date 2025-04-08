package com.a702.finafanbe.global.common.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApiClientUtil {

    private final RestTemplate restTemplate;
    private final WebClient webClient;
    private final String baseUrl = "https://finopenapi.ssafy.io/ssafy/api/v1/edu";

    public <T,R> ResponseEntity<R> callFinancialNetwork(
            String path,
            T request,
            Class<R> responseType
    ){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<T> httpEntity = new HttpEntity<>(
                request,
                headers
        );
        ResponseEntity<R> exchange = restTemplate.exchange(
                baseUrl+path,
                HttpMethod.POST,
                httpEntity,
                responseType
        );
        return ResponseEntity.ok(exchange.getBody());
    }

    /**
     * 금융 네트워크 API를 호출하고 결과를 반환합니다.
     * WebClient를 사용한 비동기 호출이지만 결과는 동기식으로 반환합니다.
     *
     * @param path API 경로
     * @param request 요청 객체
     * @param responseType 응답 타입 클래스
     * @return API 응답
     */
    public <T, R> ResponseEntity<R> callFinancialNetworkSync(
            String path,
            T request,
            Class<R> responseType
    ) {
        log.debug("금융 API 호출: {}, 요청: {}", path, request);

        return webClient.post()
                .uri(baseUrl + path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toEntity(responseType)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal ->
                                log.warn("금융 API 재시도: {}, 시도 횟수: {}",
                                        path, retrySignal.totalRetries() + 1)))
                .doOnError(error ->
                        log.error("금융 API 호출 실패: {}, 오류: {}", path, error.getMessage()))
                .onErrorResume(error -> {
                    log.error("금융 API 오류 처리: {}", error.getMessage(), error);
                    return Mono.error(error);
                })
                .block(); // 동기식 결과 반환
    }

    /**
     * 비동기 금융 네트워크 API 호출 메서드.
     * 호출자가 결과를 비동기적으로 처리할 수 있습니다.
     *
     * @param path API 경로
     * @param request 요청 객체
     * @param responseType 응답 타입 클래스
     * @return API 응답을 담은 Mono 객체
     */
    public <T, R> Mono<ResponseEntity<R>> callFinancialNetworkAsync(
            String path,
            T request,
            Class<R> responseType
    ) {
        log.debug("비동기 금융 API 호출: {}, 요청: {}", path, request);

        return webClient.post()
                .uri(baseUrl + path)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .toEntity(responseType)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal ->
                                log.warn("금융 API 재시도: {}, 시도 횟수: {}",
                                        path, retrySignal.totalRetries() + 1)))
                .doOnError(error ->
                        log.error("금융 API 호출 실패: {}, 오류: {}", path, error.getMessage()));
    }
}
