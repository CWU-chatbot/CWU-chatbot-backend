package CW.chatbot.controllers.dtos;

import CW.chatbot.commons.constants.Role;
import CW.chatbot.commons.constants.Status;
import CW.chatbot.entities.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpDto {
    private String id;
    private String password;
    private String username;
    private Role role;
    private LocalDateTime createdDate;

    public Member toEntity(String encodedPassword, Role role) {
        return Member.builder()
                .id(id)
                .password(encodedPassword)
                .username(username)
                .role(role)
                .status(Status.Active)
                .createdDate(createdDate == null ? LocalDateTime.now() : createdDate)
                .createdDate(createdDate)
                .build();
    }
}
