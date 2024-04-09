package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.JwtToken;
import CW.chatbot.controllers.dtos.MemberLoginRequestDTO;
import CW.chatbot.services.MemberService;
import CW.chatbot.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login_select") // 모든 사용자에게 허용
    public JwtToken login(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO) {
        String id = memberLoginRequestDTO.getId();
        String password = memberLoginRequestDTO.getPassword();
        JwtToken jwtToken = memberService.login(id, password);
        log.info("request id = {}, password = {}", id, password);
        log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
        return jwtToken;
    }

    @PostMapping("/test") // User 권한을 가진 사용자에게 허용
    public String test() {
        return SecurityUtil.getCurrentUsername();
    }
}
