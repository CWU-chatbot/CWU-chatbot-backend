package CW.chatbot.services;

import CW.chatbot.commons.constants.Role;
import CW.chatbot.controllers.dtos.JwtToken;
import CW.chatbot.controllers.dtos.MemberSignupDto;
import CW.chatbot.controllers.dtos.SignUpReqDto;
import CW.chatbot.entities.USERS;
import CW.chatbot.provider.JwtTokenProvider;
import CW.chatbot.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 인증 및 회원 가입 관련 서비스를 제공하는 클래스.
 * 이 클래스는 사용자 로그인 및 회원가입 로직을 처리하고, JWT 토큰을 발급하여 사용자 세션을 관리합니다.
 * <p>
 * 주요 기능:
 * - 로그인: 사용자의 ID와 비밀번호를 검증하고 JWT 토큰을 발급합니다.
 * - 회원가입: 새 사용자를 등록하고 기본적인 권한을 설정합니다.
 * <p>
 * 구성 요소:
 * - UsersRepository: 사용자 데이터에 대한 데이터베이스 접근을 처리합니다.
 * - AuthenticationManagerBuilder: Spring Security의 인증 메커니즘을 구성합니다.
 * - JwtTokenProvider: 인증 성공 후 사용자 정보를 바탕으로 JWT 토큰을 생성합니다.
 * - PasswordEncoder: 비밀번호 암호화를 담당합니다.
 * <p>
 * 메소드 설명:
 * - login(String id, String password): 사용자가 제공한 ID와 비밀번호로 인증을 시도하고, 성공 시 JWT 토큰을 반환합니다.
 * - signUp(SignUpReqDto signUpReqDto): 회원가입 요청 데이터를 바탕으로 새로운 사용자를 생성하고 저장합니다.
 * <p>
 * 예외 처리:
 * - 로그인 과정에서 ID 또는 비밀번호가 일치하지 않는 경우, AuthenticationException을 통해 처리됩니다.
 * - 회원가입 과정에서 ID가 중복되는 경우, IllegalArgumentException을 발생시켜 사용자에게 알립니다.
 * <p>
 * 사용 예:
 * - 이 서비스는 컨트롤러에서 사용자의 로그인 요청을 처리하거나 회원가입 요청을 받을 때 호출됩니다.
 * 사용자 인증이나 회원 데이터의 생성 및 검증을 담당합니다.
 *
 * @Transactional 어노테이션:
 * - 클래스 레벨에서 readOnly = true 옵션으로 설정되어 있어, 대부분의 메소드가 읽기 전용 트랜잭션에서 실행됩니다.
 * - 변경 작업이 필요한 메소드(login 및 signUp)에는 별도의 @Transactional 어노테이션이 적용되어 있어, 해당 작업 중 오류 발생 시 전체 작업을 롤백합니다.
 */

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService { // 서비스 클래스 - 로그인 메서드 구현
    private final UsersRepository usersRepository;
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
    public MemberSignupDto signUp(SignUpReqDto signUpReqDto) {
        log.info("회원가입 시도 ID: {}", signUpReqDto.getUserid());

        if (usersRepository.existsById(signUpReqDto.getUserid())) {
            log.warn("회원가입 실패 : ID 중복 : ", signUpReqDto.getUserid());
            throw new IllegalArgumentException("이미 사용중인 아이디 입니다.");
        }
        // Password 암호화
        String encodedPassword = passwordEncoder.encode(signUpReqDto.getPassword());

        // 회원가입 시, USER 역할 부여
        USERS USERS = signUpReqDto.toEntity(encodedPassword, Role.USER);
        usersRepository.save(USERS);

        log.info("회원가입 성공 ID: {}", signUpReqDto.getUserid());

        return MemberSignupDto.toDto(USERS);
    }
}
