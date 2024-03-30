package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.ChatbotException;
import CW.chatbot.controllers.dtos.UserRequestDTO;
import CW.chatbot.controllers.dtos.UserResponseDTO;
import CW.chatbot.services.ChatbotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/chat")
public class ChatbotController {

    private final ChatbotService chatbotService;

    @Autowired
    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponseDTO> getResponse(@RequestBody UserRequestDTO request) {
        try {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                throw new ChatbotException(HttpStatus.BAD_REQUEST, "Content must not be empty");
            }

            String responseContent = chatbotService.getChatbotResponse(request.getContent());
            return ResponseEntity.ok(new UserResponseDTO(200, 1, "Success", responseContent));

        } catch (ChatbotException e) {
            // ChatbotException을 직접 처리하는 경우
            return new ResponseEntity<>(new UserResponseDTO(e.getStatus().value(), 0, e.getMessage(), null), e.getStatus());
        } catch (Exception e) {
            // 기타 예외 처리
            return new ResponseEntity<>(new UserResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), 0, "An unexpected error occurred", null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}