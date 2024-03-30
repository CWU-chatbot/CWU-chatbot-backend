package CW.chatbot.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRequestDTO {
    private String content; // 사용자로부터 받는 메시지 내용
}
