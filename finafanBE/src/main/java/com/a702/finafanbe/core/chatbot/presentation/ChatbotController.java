package com.a702.finafanbe.core.chatbot.presentation;

import com.a702.finafanbe.core.chatbot.application.ChatbotService;
import com.a702.finafanbe.core.chatbot.dto.request.QuestionRequest;
import com.a702.finafanbe.core.chatbot.dto.response.AnswerResponse;
import com.a702.finafanbe.global.common.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chatbot")
@RequiredArgsConstructor
public class ChatbotController {
    private final ChatbotService chatbotService;

    @PostMapping
    public ResponseEntity<?> chatbot(@RequestBody QuestionRequest questionRequest) {

        AnswerResponse answerResponse = chatbotService.sendQueryData(questionRequest);

        return ResponseUtil.success(answerResponse);
    }
}
