package com.a702.finafanbe.global.dummy;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dummy")
@RequiredArgsConstructor
public class DummyController {

    private final DummyService dummyService;

    @GetMapping
    public void getDummy() {
        dummyService.dummyMethod();
    }
}
