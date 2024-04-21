package CW.chatbot.controllers.dtos;

import CW.chatbot.entities.Member;
import lombok.*;

/**
 *  MemberSignupDTO
 *  이 클래스는 'Member' 엔티티의 정보를 담아 클라이언트로 응답을 보낼 때 사용한다.
 *  즉, 회원가입 처리 후 사용자에게 반환되는 응답 데이터를 포함한다.
 *  주로 보안상 민감하지 않은 정보만 포함하여 클라이언트에 반환하기에 password는 제외하였다.
 */

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupDto {
    private String id;
    private String username;

    static public MemberSignupDto toDto(Member member) {
        return MemberSignupDto.builder()
                .id(member.getId())
                .username(member.getUsername())
                .build();
    }
}
