package CW.chatbot.controllers.dtos.chat;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ChatExceptionDTO extends RuntimeException {
    @Getter
    private final HttpStatus status;
    private final String message;

    public ChatExceptionDTO(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
