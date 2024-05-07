package CW.chatbot.repositories;

import CW.chatbot.entities.LOGS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 로그 데이터에 대한 데이터베이스 작업을 추상화하는 JPA 리포지토리 인터페이스.
 * 이 인터페이스는 LOGS 엔티티의 지속성을 관리하며, Spring Data JPA가 제공하는 JpaRepository를 확장하여 사용합니다.
 * JpaRepository에는 엔티티의 생성, 조회, 업데이트, 삭제(CRUD) 등의 기본적인 데이터 접근 메서드가 정의되어 있습니다.
 * <p>
 * 주요 기능:
 * - findByFolderId(int folderId): 특정 폴더 ID에 해당하는 모든 로그를 데이터베이스에서 조회합니다.
 * - findByFolderIdOrderByLogDateDesc(int folderId): 특정 폴더 ID에 해당하는 로그를 로그 날짜의 내림차순으로 정렬하여 조회합니다.
 * <p>
 * 특징:
 * - @Repository: 이 인터페이스가 데이터 접근 계층의 컴포넌트임을 나타내며, Spring IoC 컨테이너에 의해 관리됩니다.
 * <p>
 * 사용 예:
 * - LogsRepository 인터페이스를 주입받아 사용하는 서비스 또는 컨트롤러 클래스에서 이 인터페이스의 메서드를 호출하여 데이터베이스 작업을 수행할 수 있습니다.
 * 예를 들어, 특정 폴더에 관련된 로그를 조회하거나, 로그의 시간 순서에 따라 최신 로그를 조회하는 로직을 구현할 때 사용됩니다.
 */

@Repository
public interface LogsRepository extends JpaRepository<LOGS, Long> {
    List<LOGS> findByFolderId(int folderId);
    //findByFolderIdOrderByLogDateDesc 역순 정렬
}
