package CW.chatbot.services;

import CW.chatbot.commons.constants.Role;
import CW.chatbot.entities.Member;
import CW.chatbot.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService { // 서비스 클래스
    private final MemberRepository memberRepository;

    public Member saveMember(String id, String username, String password) {
        Member member = new Member(id, username, password);
        return memberRepository.save(member);
    }
}
