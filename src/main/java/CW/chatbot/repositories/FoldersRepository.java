package CW.chatbot.repositories;

import CW.chatbot.entities.FOLERS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 폴더 데이터에 대한 데이터베이스 작업을 추상화하는 JPA 리포지토리 인터페이스.
 * 이 인터페이스는 FOLERS 엔티티의 지속성을 관리하며, Spring Data JPA가 제공하는 JpaRepository를 확장하여 사용합니다.
 * JpaRepository에는 엔티티의 생성, 조회, 업데이트, 삭제(CRUD) 등의 기본적인 데이터 접근 메서드가 정의되어 있습니다.
 * <p>
 * 주요 기능:
 * - findByUserId(String userId): 주어진 사용자 ID에 해당하는 모든 폴더를 데이터베이스에서 조회합니다.
 * - deleteByFolderId(int folderId): 특정 폴더 ID를 가진 폴더를 데이터베이스에서 삭제합니다.
 * <p>
 * 특징:
 * - @Repository: 이 인터페이스가 데이터 접근 계층의 컴포넌트임을 나타내며, Spring IoC 컨테이너에 의해 관리됩니다.
 * - @Transactional: 데이터베이스 트랜잭션의 시작과 종료를 자동으로 관리합니다. 트랜잭션 관리는 주로 데이터의 일관성과 무결성을 유지하기 위해 사용됩니다.
 * deleteByFolderId 메서드에 적용되어 있어, 이 메서드 실행 시 트랜잭션이 시작되며, 메서드가 종료될 때 트랜잭션이 커밋되거나 롤백됩니다.
 * <p>
 * 사용 예:
 * - FoldersRepository 인터페이스를 주입받아 사용하는 서비스 또는 컨트롤러 클래스에서 이 인터페이스의 메서드를 호출하여 데이터베이스 작업을 수행할 수 있습니다.
 * 예를 들어, 특정 사용자의 모든 폴더를 조회하거나, 특정 폴더를 삭제하는 로직을 구현할 때 사용됩니다.
 */

@Repository
public interface FoldersRepository extends JpaRepository<FOLERS, Integer> {
    List<FOLERS> findByUserId(String userId);

    @Transactional
    void deleteByFolderId(int folderId);
}
