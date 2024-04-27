package CW.chatbot.services;

import CW.chatbot.controllers.dtos.chat.AIResDTO;
import CW.chatbot.controllers.dtos.chat.ChatExceptionDTO;
import CW.chatbot.entities.LOGS;
import CW.chatbot.commons.constants.logType;
import CW.chatbot.provider.JwtTokenProvider;
import CW.chatbot.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChatService {

    private final RestTemplate restTemplate;
    private final String chatbotServiceUrl;
    private final LogsRepository logsRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ChatService(RestTemplate restTemplate, LogsRepository logsRepository, JwtTokenProvider jwtTokenProvider, @Value("${chatbot.service.url}") String chatbotServiceUrl) {
        this.restTemplate = restTemplate;
        this.chatbotServiceUrl = chatbotServiceUrl;
        this.logsRepository = logsRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /*public String getChatbotResponse(String userQuestion, String token) throws ChatbotException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("{\"question\":\"" + userQuestion + "\"}", headers);

        try {
            ResponseEntity<AIResponseDTO> response = restTemplate.exchange(chatbotServiceUrl, HttpMethod.POST, entity, AIResponseDTO.class);

            AIResponseDTO aiResponse = response.getBody();
            if (aiResponse != null) {
                String userId = JwtTokenProvider.getUserIdFromToken(token);  // 토큰에서 userId 추출
                ChatbotResponse chatbotResponse = new ChatbotResponse(userId, aiResponse.getAnswer());
                chatbotResponseRepository.save(chatbotResponse);
                return aiResponse.getAnswer();
            } else {
                throw new ChatbotException(HttpStatus.INTERNAL_SERVER_ERROR, "Received null response from chatbot service");
            }
        } catch (Exception e) {
            throw new ChatbotException(HttpStatus.INTERNAL_SERVER_ERROR, "Error communicating with chatbot service");
        }
    }*/

    // ChatbotService 클래스 내부에서 토큰 처리 부분 수정
    public String getChatbotResponse(int folderId, String userQuestion, String token) throws ChatExceptionDTO {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 하드코딩으로 응답 설정
        AIResDTO aiResponse = new AIResDTO();

        if ("안녕".equals(userQuestion)) {
            aiResponse.setAnswer("안녕하세요!");
        } else if ("컴퓨터공학과 학회실은 어디에 있어?".equals(userQuestion)) {
            aiResponse.setAnswer("인천광역시 미추홀구 숙골로 113(청운대학교 인천캠퍼스) 838호에요.");
        } else {
            aiResponse.setAnswer("그 질문에 답은 잘 모르겠어요");
        }

        try {
            LOGS userRequest = new LOGS(folderId, logType.User, userQuestion);
            logsRepository.save(userRequest);
            LOGS LOGS = new LOGS(folderId, logType.Ai, aiResponse.getAnswer());
            logsRepository.save(LOGS);

            return aiResponse.getAnswer();
        } catch (Exception e) {
            throw new ChatExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR, "Error extracting user from token: " + e.getMessage());
        }
    }
}
