package CW.chatbot.services;

import CW.chatbot.controllers.dtos.AIResponseDTO;
import CW.chatbot.controllers.dtos.ChatbotException;
import CW.chatbot.entities.ChatbotResponse;
import CW.chatbot.commons.constants.logType;
import CW.chatbot.provider.JwtTokenProvider;
import CW.chatbot.repositories.ChatbotResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * ChatbotService 클래스는 챗봇과의 통신을 담당하는 서비스 계층입니다.
 * 이 클래스는 사용자의 질문을 인공지능 챗봇 서비스로 전달하고, 그 응답을 받아 반환하는 역할을 합니다.
 *
 * 주요 기능:
 * - 사용자 질문을 인공지능 챗봇 서비스에 전송하고, 그에 대한 답변을 받습니다.
 *
 * 주요 메소드:
 * - getChatbotResponse(String userQuestion): 사용자의 질문을 받아 인공지능 챗봇 서비스에 전송하고, 받은 답변을 문자열로 반환합니다.
 *   통신 과정에서 문제가 발생하면 ChatbotException을 던집니다.
 *
 * @Service 어노테이션은 이 클래스가 비즈니스 로직을 처리하는 서비스 계층의 컴포넌트임을 나타냅니다.
 * @Autowired 어노테이션은 Spring의 의존성 주입 기능을 사용하여 RestTemplate 객체와 chatbotServiceUrl 값을 자동으로 주입받습니다.
 *
 * chatbotServiceUrl은 application.properties 파일에서 설정된 인공지능 챗봇 서비스의 URL입니다.
 */
@Service
public class ChatbotService {

    private final RestTemplate restTemplate;
    private final String chatbotServiceUrl;
    private final ChatbotResponseRepository chatbotResponseRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ChatbotService(RestTemplate restTemplate, ChatbotResponseRepository chatbotResponseRepository, JwtTokenProvider jwtTokenProvider, @Value("${chatbot.service.url}") String chatbotServiceUrl) {
        this.restTemplate = restTemplate;
        this.chatbotServiceUrl = chatbotServiceUrl;
        this.chatbotResponseRepository = chatbotResponseRepository;
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
    public String getChatbotResponse(int folderId, String userQuestion, String token) throws ChatbotException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 하드코딩으로 응답 설정
        AIResponseDTO aiResponse = new AIResponseDTO();

        if ("안녕".equals(userQuestion)) {
            aiResponse.setAnswer("안녕하세요!");
        } else if ("컴퓨터공학과 학회실은 어디에 있어?".equals(userQuestion)) {
            aiResponse.setAnswer("인천광역시 미추홀구 숙골로 113(청운대학교 인천캠퍼스) 838호에요.");
        }
        else {
            aiResponse.setAnswer("그 질문에 답은 잘 모르겠어요");
        }

        try {
            ChatbotResponse userRequest = new ChatbotResponse(folderId, logType.User, userQuestion);
            chatbotResponseRepository.save(userRequest);
            ChatbotResponse chatbotResponse = new ChatbotResponse(folderId, logType.Ai, aiResponse.getAnswer());
            chatbotResponseRepository.save(chatbotResponse);

            return aiResponse.getAnswer();
        } catch (Exception e) {
            throw new ChatbotException(HttpStatus.INTERNAL_SERVER_ERROR, "Error extracting user from token: " + e.getMessage());
        }
    }
}
