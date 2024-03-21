package CW.chatbot.controllers;

import CW.chatbot.entities.Member;
import CW.chatbot.controllers.dtos.MemberRequestDTO;
import CW.chatbot.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
//@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/members")
    @ResponseBody
    public Member createMember(@RequestBody MemberRequestDTO memberDTO) {
        return memberService.saveMember(
                memberDTO.getId(),
                memberDTO.getUsername(),
                memberDTO.getPassword()
        );
    }
}
