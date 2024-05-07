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

/**
 * 채팅 관련 요청을 처리하는 컨트롤러 클래스.
 * 이 클래스는 클라이언트로부터 채팅 메시지와 관련 정보를 받아 서비스 계층으로 전달하는 책임을 지닙니다.
 * 서비스 계층은 이 정보를 바탕으로 실제 채팅 로직을 처리하며, 그 결과를 다시 이 컨트롤러로 반환합니다.
 * 컨트롤러는 이 결과를 가공하여 HTTP 응답 형태로 클라이언트에게 전달하게 됩니다.
 * <p>
 * 주요 기능:
 * 1. **인증 정보 처리**: 클라이언트로부터 전달받은 인증 정보 (Authorization 헤더)를 처리하여 토큰을 추출하고 검증합니다.
 * 2. **요청 데이터 검증**: 클라이언트로부터 받은 요청 데이터의 유효성을 검증합니다. 예를 들어, 채팅 내용이 비어 있으면 안 됩니다.
 * 3. **채팅 처리 요청**: 검증된 요청 데이터를 서비스 계층에 전달하여 채팅 처리를 요청합니다. 이 때, 채팅 내용뿐만 아니라 폴더 ID 등 추가 정보도 함께 전달될 수 있습니다.
 * 4. **응답 생성 및 반환**: 서비스 계층으로부터 받은 채팅 처리 결과를 클라이언트에게 전달할 응답 객체로 포맷하여 반환합니다. 성공적인 처리의 경우, 성공 메시지와 데이터를 포함한 응답을, 오류 발생 시 적절한 오류 메시지와 상태 코드를 포함한 응답을 반환합니다.
 * 5. **예외 처리**: 채팅 처리 과정 중 발생할 수 있는 예외를 적절히 처리하고, 예외 유형에 따라 클라이언트에게 적절한 HTTP 상태 코드와 오류 메시지를 제공합니다.
 * <p>
 * 모든 채팅 관련 요청은 이 경로를 통해 이 클래스로 라우팅됩니다.
 */


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

            if (request.getFolderId() == null || request.getFolderId() <= 0) {
                return ResponseEntity.badRequest().body(new ChatResDTO(400, "Folder ID must not be empty", ""));
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
