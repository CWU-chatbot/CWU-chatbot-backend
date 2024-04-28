package CW.chatbot.repositories;

import CW.chatbot.entities.FOLERS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FoldersRepository extends JpaRepository<FOLERS, Integer> {
    List<FOLERS> findByUserId(String userId);

    @Transactional
    void deleteByFolderId(int folderId);
}
