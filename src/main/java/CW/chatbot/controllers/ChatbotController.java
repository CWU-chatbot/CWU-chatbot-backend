package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.ChatbotException;
import CW.chatbot.controllers.dtos.ChatbotRequestDTO;
import CW.chatbot.controllers.dtos.ChatbotResponseDTO;
import CW.chatbot.services.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ChatbotController 클래스는 챗봇 관련 HTTP 요청을 처리하는 컨트롤러입니다.
 * 이 클래스는 사용자로부터 챗봇 관련 요청을 받아, 적절한 서비스 로직을 호출하고 결과를 반환합니다.
 *
 * 주요 기능:
 * - 사용자의 챗봇 요청을 받아 처리하고, 처리 결과를 반환합니다.
 *
 * 주요 메소드:
 * - getResponse(@RequestBody ChatbotRequestDTO request): 사용자로부터 챗봇에 대한 요청을 받아, 그 요청에 대한 응답을 반환합니다.
 *   요청이 유효하지 않거나 처리 중 에러가 발생한 경우, 적절한 HTTP 상태 코드와 에러 메시지를 포함한 응답을 반환합니다.
 */
@RestController
@RequestMapping(value = "/chat")
public class ChatbotController {

    private final ChatbotService chatbotService;

    @Autowired
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatbotResponseDTO> getResponse(@RequestBody ChatbotRequestDTO request) {
        try {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ChatbotResponseDTO(400, 0, "Content must not be empty", ""));
            }

            String responseContent = chatbotService.getChatbotResponse(request.getContent());
            return ResponseEntity.ok(new ChatbotResponseDTO(200, 1, "Success", responseContent));
        } catch (ChatbotException e) {
            return ResponseEntity.status(e.getStatus()).body(new ChatbotResponseDTO(e.getStatus().value(), 0, e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ChatbotResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), 0, "An unexpected error occurred", null));
        }
    }
}
