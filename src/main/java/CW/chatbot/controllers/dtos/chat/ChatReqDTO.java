package CW.chatbot.controllers.dtos.chat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChatReqDTO {
    private Integer folderId;
    private String content;
}
