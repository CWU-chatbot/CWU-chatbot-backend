package CW.chatbot.controllers.dtos.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatResDTO {
    private int resultCode;
    private String resultMessage;
    private String content;
}
