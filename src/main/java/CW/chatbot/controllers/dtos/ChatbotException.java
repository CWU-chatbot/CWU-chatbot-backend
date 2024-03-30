package CW.chatbot.controllers.dtos;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * ChatbotException 클래스는 챗봇 서비스 관련 예외 처리를 위해 사용됩니다.
 * 이 클래스는 RuntimeException을 확장하며, 챗봇 서비스와 관련된 오류 상황에서 사용됩니다.
 *
 * 이 예외 클래스는 HTTP 상태 코드와 오류 메시지를 포함합니다.
 * - status 필드는 HTTP 응답의 상태 코드를 저장합니다. 예를 들어, 400(Bad Request), 404(Not Found) 등
 *   클라이언트나 서버의 오류 상황을 나타내는데 사용됩니다.
 * - message 필드는 오류 상황을 설명하는 문자열 메시지를 저장합니다. 이 메시지는 예외가 발생했을 때
 *   클라이언트에게 보여줄 오류 메시지를 제공합니다.
 *
 * 이 클래스는 특정 상황에서 발생하는 예외를 캡슐화하고, 이를 처리하는 과정에서 HTTP 상태 코드와
 * 관련 메시지를 쉽게 관리할 수 있도록 도와줍니다.
 */
public class ChatbotException extends RuntimeException {
    @Getter
    private final HttpStatus status;
    private final String message;

    public ChatbotException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message; // 오류 메시지를 반환
    }
}
