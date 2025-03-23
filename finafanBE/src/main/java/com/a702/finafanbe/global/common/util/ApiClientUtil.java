package com.a702.finafanbe.global.common.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ApiClientUtil {

    private final RestTemplate restTemplate;
    private final String baseUrl = "https://finopenapi.ssafy.io/ssafy/api/v1/edu";

    public <T,R> ResponseEntity<R> callFinancialNetwork(String path, T request, Class<R> responseType){
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
}
