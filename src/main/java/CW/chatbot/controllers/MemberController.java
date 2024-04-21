package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.*;
import CW.chatbot.services.MemberService;
import CW.chatbot.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(path = "/sign_in", produces = MediaType.APPLICATION_JSON_VALUE) // 모든 사용자에게 허용
    public ResponseEntity<SignInResponseDTO> signIn(@RequestBody SignInRequestDTO signInRequestDTO) {
        try {
            if (signInRequestDTO.getId() == null || signInRequestDTO.getPassword() == null) {
                return ResponseEntity.badRequest().body(new SignInResponseDTO(400, "Id or Password not be empty", "", ""));
            }

            String id = signInRequestDTO.getId();
            String password = signInRequestDTO.getPassword();
            JwtToken jwtToken = memberService.login(id, password); // token 부여
            String responseAccessToken = jwtToken.getAccessToken();
            String responseRefreshToken = jwtToken.getRefreshToken();
            log.info("request id = {}, password = {}", id, password); // 후에 비밀번호 로그 제거 예정
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
            return ResponseEntity.ok(new SignInResponseDTO(200, "Success", responseAccessToken, responseRefreshToken));
        } catch (SignInException e) {
            return ResponseEntity.status(e.getStatus()).body(new SignInResponseDTO(e.getStatus().value(), e.getMessage(), null, null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new SignInResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null, null));
        }
    }

//    @PostMapping("/test") // User 권한을 가진 사용자에게 허용
//    public String test() {
//        return SecurityUtil.getCurrentUsername();
//    }

    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpDto signUpDto) {
        log.debug("Received signUp request with ID: {}", signUpDto.getUserid());
        if (signUpDto.getUserid() == null || signUpDto.getPassword() == null || signUpDto.getNickname() == null) {
            log.debug("Bad Request: Missign required fields");
            return ResponseEntity.badRequest().body(new SignUpResponseDTO(400, "Invalid request", "Failure"));
        }
        try {
            memberService.signUp(signUpDto);
            log.debug("SignUp Success: {}", signUpDto.getUserid());
            return ResponseEntity.ok(new SignUpResponseDTO(200, "Success", "Success"));
        } catch (IllegalArgumentException e) {
            log.error("SignUp Failure: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new SignUpResponseDTO(400, e.getMessage(), "Failure"));
        } catch (Exception e) {
            log.error("Internal Server Error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new SignUpResponseDTO(500, "Internal Server Error", "Failure"));
        }
    }

}
