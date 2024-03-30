package CW.chatbot.services;

import CW.chatbot.controllers.dtos.ChatbotResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
public class ChatbotService {

    private final RestTemplate restTemplate;
    private final String chatbotServiceUrl;

    @Autowired
    public ChatbotService(RestTemplate restTemplate, @Value("${chatbot.service.url}") String chatbotServiceUrl) {
        this.restTemplate = restTemplate;
        this.chatbotServiceUrl = chatbotServiceUrl;
    }

    public String getChatbotResponse(String userQuestion) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"question\":\"" + userQuestion + "\"}", headers);

        ResponseEntity<ChatbotResponseDTO> response = restTemplate.exchange(
                chatbotServiceUrl,
                HttpMethod.POST,
                entity,
                ChatbotResponseDTO.class // 인공지능 서버의 응답 형식에 맞게 ChatbotResponseDTO 사용
        );

        // 인공지능 서버에서 응답 받은 내용 확인 및 반환
        System.out.println("챗봇 응답 : " + Objects.requireNonNull(response.getBody()).getAnswer());
        return Objects.requireNonNull(response.getBody()).getAnswer();
    }
}