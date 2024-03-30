package CW.chatbot.controllers.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * 이 클래스는 인공지능 서비스로부터 받은 응답을 매핑하기 위해 사용됩니다.
 * 인공지능 서비스는 사용자의 질문에 대한 답변을 JSON 형태로 반환합니다.
 * 이 JSON 객체는 'answer' 키에 대한 값을 포함하며, 이 값은 사용자의 질문에 대한 답변입니다.
 * AIResponseDTO 클래스는 이 'answer' 키에 해당하는 값을 저장하기 위한 필드와 그에 대한 접근 메소드를 제공합니다.
 */
@Setter
@Getter
public class AIResponseDTO {
    private String answer;
}