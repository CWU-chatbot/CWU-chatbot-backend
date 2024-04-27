package CW.chatbot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "FOLDERS")
public final class FOLERS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int folderId;

    @JsonIgnore
    @Column(name = "userId", nullable = false)
    private String userId;

    @Column(name = "folderContent", nullable = false)
    private String folderContent;

    @CreationTimestamp
    @Column(name = "folderDate", nullable = false)
    private LocalDateTime folderDate;

    public FOLERS() {
    }
}
