package CW.chatbot.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class JwtToken {
    private String grantType; // JWT에 대한 인증 타입
    private String accessToken;
    private String refreshToken;

    // ex) Authorization: Bearer <access_token>
}
