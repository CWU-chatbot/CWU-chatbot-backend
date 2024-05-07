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

/**
 * 챗봇 응답과 관련된 서비스 로직을 제공하는 서비스 클래스.
 * 이 클래스는 사용자 질문에 대한 챗봇의 응답을 처리하고, 해당 대화 로그를 데이터베이스에 저장하는 기능을 수행합니다.
 * <p>
 * 주요 기능:
 * - 챗봇 서비스와의 통신을 관리하여 사용자 질문에 대한 답변을 가져옵니다.
 * - 사용자의 질문과 챗봇의 응답을 로그로 저장합니다.
 * <p>
 * 구성 요소:
 * - RestTemplate: HTTP 요청을 보내기 위한 스프링의 헬퍼 클래스.
 * - LogsRepository: 대화 로그를 데이터베이스에 저장하기 위한 JPA 리포지토리.
 * - JwtTokenProvider: JWT 토큰에서 사용자 정보를 추출하는 유틸리티 클래스.
 * - chatbotServiceUrl: 외부 챗봇 서비스의 URL, application.properties 파일에서 설정된 값을 주입받습니다.
 * <p>
 * 메소드 설명:
 * - getChatbotResponse(int folderId, String userQuestion, String token): 사용자의 질문을 받아 챗봇 서비스에 요청하고,
 * 챗봇의 응답을 반환합니다. 사용자 질문과 챗봇의 응답은 LOGS 테이블에 저장됩니다. 메소드는 사용자의 토큰을 검증하고,
 * 토큰에서 사용자 ID를 추출하여 사용합니다. 문제가 발생한 경우 ChatExceptionDTO 예외를 발생시킵니다.
 * <p>
 * 예외 처리:
 * - ChatExceptionDTO: 챗봇 서비스 통신 중 발생한 예외를 적절히 처리하고, 상태 코드와 에러 메시지를 포함하는
 * 사용자 정의 예외를 발생시킵니다.
 * <p>
 * 사용 예:
 * - 이 서비스는 챗봇과의 인터랙션을 관리하는 컨트롤러에서 호출됩니다. 컨트롤러는 사용자의 HTTP 요청을 받아 이 서비스의
 * 메소드를 사용하여 챗봇 응답을 처리하고 최종 결과를 사용자에게 반환합니다.
 */

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
