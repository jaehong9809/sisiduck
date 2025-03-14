package com.a702.finafanbe.global.dummy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class DummyService {

    @Autowired
    private RestTemplate restTemplate;

    public void dummyMethod() {
        String url = "https://finopenapi.ssafy.io/ssafy/api/v1/edu/demandDeposit/inquireDemandDepositList";
        HttpHeaders headers = new HttpHeaders();
        headers.set("apiName","inquireDemandDepositList");
        headers.set("transmissionDate", LocalDate.now().toString());
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        System.out.println(response.getBody());
    }
}
