package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.JwtToken;
import CW.chatbot.controllers.dtos.MemberLoginRequestDTO;
import CW.chatbot.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login_select")
    public JwtToken login(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO) {
        String id = memberLoginRequestDTO.getId();
        String password = memberLoginRequestDTO.getPassword();
        JwtToken jwtToken = memberService.login(id, password);
        return jwtToken;
    }
}
