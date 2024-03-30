package CW.chatbot.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * ChatbotRequestDTO 클래스는 사용자로부터 챗봇 서비스에 전달되는 요청 데이터를 나타냅니다.
 * 이 데이터 전송 객체(Data Transfer Object, DTO)는 사용자의 요청을 캡슐화하여, 서비스 계층과
 * 컨트롤러 계층 간의 데이터 전달을 단순화합니다.
 *
 * 주요 필드:
 * - content: 사용자로부터 입력받은 메시지의 내용입니다. 챗봇에게 전달되어 처리될 실제 텍스트 데이터입니다.
 */
@Setter
@Getter
public class ChatbotRequestDTO {
    private String content;
}
