package CW.chatbot.client;

import CW.chatbot.controllers.dtos.ChatbotResponseDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ChatbotClient {
    public static void main(String[] args) {

        //@requestMapping("/chat") ,post만 사용 예정
        //앤드포인트 URL
        final String url = "http://localhost:8080/chat";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //요청 바디에 포함할 데이터를 json 형식으로 직렬화
        //조금 더 공부 필요
        String requestBody = "{\"key\": \"value\"}"; //예시 데이터

        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        //지정된 uri로 post요청, 응답은 ChatbotResponseDTO 클래스로 받음
        ChatbotResponseDTO response = restTemplate.postForObject(url, requestEntity, ChatbotResponseDTO.class);

        //응답 데이터 출력
        System.out.println("Status: " + response.getStatus());
        System.out.println("Result Code: " + response.getResultCode());
        System.out.println("Result Message: " + response.getResultMessage());
        System.out.println("Data :" + response.getData());
    }
}