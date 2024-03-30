package CW.chatbot.controllers.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * ChatbotResponseDTO 클래스는 챗봇 서비스로부터의 응답 데이터를 나타냅니다.
 * 이 데이터 전송 객체(Data Transfer Object, DTO)는 챗봇 서비스의 처리 결과와 메시지를 포함하여,
 * 서비스 계층에서 컨트롤러 계층으로 데이터를 전달하는 데 사용됩니다.
 *
 * 주요 필드:
 * - status: HTTP 응답 코드를 나타냅니다. 예를 들어, 200은 성공, 400은 잘못된 요청 등입니다.
 * - resultCode: 처리 결과를 나타내는 코드입니다. 일반적으로 1은 성공, 0은 실패를 의미합니다.
 * - resultMessage: 처리 결과에 대한 메시지입니다. 예를 들어, "Success"나 "Error occurred" 등의 메시지가 될 수 있습니다.
 * - content: 챗봇의 응답 내용입니다. 사용자에게 보여질 실제 메시지 또는 정보를 포함합니다.
 */
@Getter
@Setter
@AllArgsConstructor
public class ChatbotResponseDTO {
    private int status;
    private int resultCode;
    private String resultMessage;
    private String content;
}
