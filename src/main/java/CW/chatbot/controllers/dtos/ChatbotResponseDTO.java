package CW.chatbot.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatbotResponseDTO {
    private String answer;

    public ChatbotResponseDTO(String answer) {
        this.answer = answer;
    }
}