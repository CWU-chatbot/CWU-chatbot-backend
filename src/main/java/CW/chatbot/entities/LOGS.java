package CW.chatbot.entities;

import CW.chatbot.commons.constants.logType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "LOGS")
public final class LOGS {

    @Id
    @JsonIgnore
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int logId;

    @JsonIgnore
    @Column(name = "folderId", nullable = false)
    private int folderId;

    @Enumerated(EnumType.STRING)
    @Column(name = "logType", nullable = false)
    private logType logType;

    @Column(name = "logContent", nullable = false)
    private String logContent;

    @CreationTimestamp
    @Column(name = "logDate", nullable = false)
    private LocalDateTime logDate;

    public LOGS() {
    }

    public LOGS(int folderId, logType logType, String logContent) {
        this.folderId = folderId;
        this.logType = logType;
        this.logContent = logContent;
    }
}
