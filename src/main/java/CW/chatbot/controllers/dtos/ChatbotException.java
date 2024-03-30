package CW.chatbot.controllers.dtos;

import lombok.Getter;
import org.springframework.http.HttpStatus;

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
        return message;
    }
}