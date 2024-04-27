package CW.chatbot.repositories;

import CW.chatbot.entities.LOGS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogsRepository extends JpaRepository<LOGS, Long> {
    List<LOGS> findByFolderId(int folderId);
    //findByFolderIdOrderByLogDateDesc 역순 정렬
}
