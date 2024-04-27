package CW.chatbot.controllers.dtos.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatReqDTO {
    private int folderId;
    private String content;
}
