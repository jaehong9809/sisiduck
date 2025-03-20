package com.a702.finafanbe.core.chatbot.presentation;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatbot")
public class ChatbotController {

    @GetMapping()
    public ResponseEntity<?> chatbot() {
        return ResponseEntity.ok().build();
    }
}
