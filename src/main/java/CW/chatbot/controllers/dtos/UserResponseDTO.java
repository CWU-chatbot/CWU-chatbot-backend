package CW.chatbot.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponseDTO {
    private int status;
    private int resultCode;
    private String resultMessage;
    private String content;
}
