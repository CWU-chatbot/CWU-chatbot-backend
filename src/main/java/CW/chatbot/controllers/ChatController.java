package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.chat.ChatExceptionDTO;
import CW.chatbot.controllers.dtos.chat.ChatReqDTO;
import CW.chatbot.controllers.dtos.chat.ChatResDTO;
import CW.chatbot.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chat")
public class ChatController {

    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChatResDTO> getResponse(@RequestBody ChatReqDTO request, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", "").trim();
        try {
            if (request.getContent() == null || request.getContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new ChatResDTO(400, "Content must not be empty", ""));
            }
            String responseContent = chatService.getChatbotResponse(request.getFolderId(), request.getContent(), token);
            return ResponseEntity.ok(new ChatResDTO(200, "Success", responseContent));
        } catch (ChatExceptionDTO e) {
            return ResponseEntity.status(e.getStatus()).body(new ChatResDTO(e.getStatus().value(), e.getMessage(), null));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ChatResDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred: " + e.getMessage(), null));
        }
    }
}
