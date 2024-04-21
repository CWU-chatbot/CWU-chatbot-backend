package CW.chatbot.services;

import CW.chatbot.commons.constants.Role;
import CW.chatbot.controllers.dtos.JwtToken;
import CW.chatbot.controllers.dtos.MemberSignupDto;
import CW.chatbot.controllers.dtos.SignUpDto;
import CW.chatbot.entities.Member;
import CW.chatbot.provider.JwtTokenProvider;
import CW.chatbot.repositories.MemberRepository;
import ch.qos.logback.classic.encoder.JsonEncoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService { // 서비스 클래스 - 로그인 메서드 구현
    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional // 메서드가 포함하고 있는 작업 중에 하나라도 실패할 경우 전체 작업을 취소
    public JwtToken login(String id, String password) {
        // Login ID/PW 기반으로 Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(id, password);

        // 실제 검증 부분 - 사용자 비밀번호 체크
        // authenticate() 메서드를 통해 요청된 Member에 대한 검증이 진행
        // authenticate 메서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 인증 정보를 기반으로 JWT 토큰 생성 -> 검증 정상 통과
        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        return jwtToken;
    }

    @Transactional
    public MemberSignupDto signUp(SignUpDto signUpDto) {
        if (memberRepository.existsById(signUpDto.getId())) {
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpDto.getPassword());

        Member member = signUpDto.toEntity(encodedPassword, Role.USER);
        Member savedMember = memberRepository.save(member);

        return MemberSignupDto.toDto(savedMember);
    }
}
