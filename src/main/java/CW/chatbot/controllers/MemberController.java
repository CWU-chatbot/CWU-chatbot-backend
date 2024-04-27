package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.*;
import CW.chatbot.services.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping(value = "/sign_in", produces = MediaType.APPLICATION_JSON_VALUE) // 모든 사용자에게 허용
    public ResponseEntity<SignInResDTO> signIn(@RequestBody SignInReqDTO signInReqDTO) {
        try {
            if (signInReqDTO.getId() == null || signInReqDTO.getPassword() == null) {
                return ResponseEntity.badRequest().body(new SignInResDTO(400, "Id or Password not be empty", "", ""));
            }

            String id = signInReqDTO.getId();
            String password = signInReqDTO.getPassword();
            JwtToken jwtToken = memberService.login(id, password); // token 부여
            String responseAccessToken = jwtToken.getAccessToken();
            String responseRefreshToken = jwtToken.getRefreshToken();
            log.info("request id = {}, password = {}", id, password); // 후에 비밀번호 로그 제거 예정
            log.info("jwtToken accessToken = {}, refreshToken = {}", jwtToken.getAccessToken(), jwtToken.getRefreshToken());
            return ResponseEntity.ok(new SignInResDTO(200, "Success", responseAccessToken, responseRefreshToken));
        } catch (SignInException e) {
            return ResponseEntity.status(e.getStatus()).body(new SignInResDTO(e.getStatus().value(), e.getMessage(), null, null));
        } catch (Exception e) {
            e.printStackTrace();
            log.error("An unexpected error occurred: " + e.getMessage());
            return ResponseEntity.internalServerError().body(new SignInResDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null, null));
        }
    }

    @PostMapping(value = "/sign_up")
    public ResponseEntity<SignUpResponseDTO> signUp(@RequestBody SignUpReqDto signUpReqDto) {
        log.debug("Received signUp request with ID: {}", signUpReqDto.getUserid());
        if (signUpReqDto.getUserid() == null || signUpReqDto.getPassword() == null || signUpReqDto.getNickname() == null) {
            log.debug("Bad Request: Missign required fields");
            return ResponseEntity.badRequest().body(new SignUpResponseDTO(400, "Invalid request", "Failure"));
        }
        try {
            memberService.signUp(signUpReqDto);
            log.debug("SignUp Success: {}", signUpReqDto.getUserid());
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
