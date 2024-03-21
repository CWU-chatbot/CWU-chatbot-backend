package CW.chatbot.controllers.dtos;

import CW.chatbot.commons.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberRequestDTO {
    @NotNull
    private String id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String role;
}
