package CW.chatbot.entities;

import CW.chatbot.commons.constants.logType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "LOGS")
public final class ChatbotResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    @Column(name = "folderId", nullable = false)
    private int folderId;

    @Enumerated(EnumType.STRING)  // EnumType.STRING을 사용하여 데이터베이스에 문자열로 저장
    @Column(name = "logType", nullable = false)
    private logType logType;

    @Column(name = "logContent", nullable = false)
    private String logContent;

    @CreationTimestamp
    @Column(name = "logDate", nullable = false)
    private LocalDateTime logDate;

    public ChatbotResponse() {}

    public ChatbotResponse(int folderId, logType logType, String logContent) {
        this.folderId = folderId;
        this.logType = logType;
        this.logContent = logContent;
    }
}
