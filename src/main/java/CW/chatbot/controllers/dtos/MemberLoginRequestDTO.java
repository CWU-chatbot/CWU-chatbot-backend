package CW.chatbot.controllers.dtos;

import lombok.Data;

@Data
public class MemberLoginRequestDTO {
    private String id;
    private String password;
}
