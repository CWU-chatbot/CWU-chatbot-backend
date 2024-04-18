package CW.chatbot.controllers.dtos;

import CW.chatbot.entities.Member;
import lombok.*;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupDto {
    private Long no;
    private String id;
    private String username;

    static public MemberSignupDto toDto(Member member) {
        return MemberSignupDto.builder()
                //.no(member.getNo())
                .id(member.getId())
                .username(member.getUsername())
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                //.no(no)
                .id(id)
                .username(username)
                .build();
    }
}
