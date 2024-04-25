package CW.chatbot.repositories;

import CW.chatbot.entities.ChatbotResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatbotResponseRepository extends JpaRepository<ChatbotResponse, Long> {
}
