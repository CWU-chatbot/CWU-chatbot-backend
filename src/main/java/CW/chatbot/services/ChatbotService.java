package CW.chatbot.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

        // 서버로부터의 응답을 String 으로 받음
        String jsonResponse = restTemplate.postForObject(chatbotServiceUrl, entity, String.class);

        // ObjectMapper를 사용하여 String 응답에서 "answer" 필드 추출
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(jsonResponse);
            String answer = root.path("answer").asText();
            System.out.println("챗봇 응답 : " + answer); // 로그 출력
            return answer;
        } catch (Exception e) {
            System.out.println("응답 처리 중 오류 발생: " + e.getMessage());
            return e.getMessage();
        }
    }
}
