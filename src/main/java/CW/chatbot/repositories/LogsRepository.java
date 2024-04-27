package CW.chatbot.repositories;

import CW.chatbot.entities.LOGS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogsRepository extends JpaRepository<LOGS, Long> {
}
