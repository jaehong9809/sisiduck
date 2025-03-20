package com.a702.finafanbe.core.chatbot.application;

import com.a702.finafanbe.core.chatbot.dto.request.QuestionRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ChatbotService {
    private final WebClient webClient;

    public ChatbotService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000/ai").build();
    }

    public String sendQueryData(QuestionRequest req) {
        if (req == null || req.getQuestion().isBlank()) {
            return "No query to send.";
        }

        try {
            String response = webClient.post()
                    .uri("/chatbot/ask")  // 챗봇 질문을 보내는 엔드포인트
                    .bodyValue(req)  // DTO 객체 전송
                    .retrieve()
                    .bodyToMono(String.class)  // 응답을 String으로 변환
                    .block();  // 동기적으로 응답 대기 후 반환

            return response != null ? response : "No response from server.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
