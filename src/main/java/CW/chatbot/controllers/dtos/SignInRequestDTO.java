package CW.chatbot.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class SignInRequestDTO {
    private String id;
    private String password;
}
