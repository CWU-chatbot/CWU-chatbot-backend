package CW.chatbot.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 폴더 데이터를 관리하는 엔티티 클래스.
 * 이 클래스는 데이터베이스의 'FOLDERS' 테이블과 매핑되어 폴더 관련 데이터를 표현합니다.
 * <p>
 * 속성:
 * - folderId: 폴더의 고유 식별자입니다. 데이터베이스에서 자동으로 생성됩니다 (Auto Increment).
 * - userId: 폴더를 소유한 사용자의 식별자입니다. 이 필드는 외부에 노출되지 않도록 설정되어 있습니다.
 * - folderContent: 폴더의 내용을 저장하는 필드입니다. 폴더 내용은 비어 있으면 안 됩니다.
 * - folderDate: 폴더가 생성된 날짜와 시간입니다. 이 필드는 자동으로 현재 시간이 기록되도록 설정되어 있습니다.
 * <p>
 * 주요 기능 및 특징:
 * - @Entity: 이 클래스가 JPA 엔티티임을 나타냅니다.
 * - @Table: 이 클래스가 매핑될 데이터베이스 테이블의 이름을 지정합니다.
 * - @Id 및 @GeneratedValue: 'folderId' 필드가 기본 키 역할을 하며, 값은 자동으로 생성됩니다.
 * - @JsonIgnore: 'userId' 필드가 JSON 직렬화 과정에서 제외되도록 설정합니다. API 응답 시 이 정보를 숨깁니다.
 * - @Column: 각 필드가 데이터베이스 컬럼과 어떻게 매핑될지 설정합니다. 'nullable = false'는 해당 필드가 비어 있을 수 없음을 의미합니다.
 * - @CreationTimestamp: 'folderDate' 필드에 현재 시간을 자동으로 기록합니다.
 * - Lombok 라이브러리의 @Setter, @Getter: 필드에 대한 getter 및 setter 메소드를 자동으로 생성합니다.
 * <p>
 * 생성자:
 * - 매개변수가 있는 생성자는 'userId'와 'folderContent'를 초기화하는 데 사용됩니다.
 * - 기본 생성자는 JPA에서 엔티티의 인스턴스를 생성할 때 필요합니다.
 */

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

    public FOLERS(String userId, String folderContent) {
        this.userId = userId;
        this.folderContent = folderContent;
    }

    public FOLERS() {
    }
}
