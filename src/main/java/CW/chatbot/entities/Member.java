package CW.chatbot.entities;

import CW.chatbot.commons.constants.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Data
public class Member { // 사용자 정보
    @Id @GeneratedValue
    private Long userNo; // 자동 생성 번호
    @Column(length=40, nullable = false, unique = true)
    private String id;
    @Column(length = 40, nullable = false)
    private String username;
    @Column(length = 50, nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // 기본값으로 user 설정
    @Column(insertable = false)
    private LocalDateTime registrationDate;

//    @PrePersist
//    protected void onCreate() {
//        registrationDate = LocalDateTime.now();
//    }

//    public Member(String id, String username, String password) {
//        this.id = id;
//        this.username = username;
//        this.password = password;
//        this.role = Role.USER;
//        this.registrationDate = LocalDateTime.now();
//    }

    public Member() {
        this.registrationDate = LocalDateTime.now();
    }

    public Member(String id, String username, String password) {
        this();
        this.id = id;
        this.username = username;
        this.password = password;
    }
}
