package CW.chatbot.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatbotRequestDTO {
    private String content; // 사용자의 질문을 담는 필드
}
