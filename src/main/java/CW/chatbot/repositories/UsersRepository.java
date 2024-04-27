package CW.chatbot.repositories;

import CW.chatbot.entities.USERS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<USERS, String> {
    Optional<USERS> findById(String id);
}
