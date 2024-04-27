package CW.chatbot.services;

import CW.chatbot.controllers.dtos.loadfolder.LoadFolderDataDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderResponseDTO;
import CW.chatbot.entities.FOLERS;
import CW.chatbot.provider.JwtTokenProvider;
import CW.chatbot.repositories.FoldersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    private final JwtTokenProvider jwtTokenProvider;
    private final FoldersRepository foldersRepository;

    @Autowired
    public FolderService(JwtTokenProvider jwtTokenProvider, FoldersRepository foldersRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.foldersRepository = foldersRepository;
    }

    public ResponseEntity<LoadFolderResponseDTO> loadFolders(String token) {
        String userId = jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", "").trim());
        List<FOLERS> dataList = foldersRepository.findByUserId(userId);

        LoadFolderDataDTO data = new LoadFolderDataDTO(dataList);

        LoadFolderResponseDTO response = new LoadFolderResponseDTO(200, "Success", data);
        return ResponseEntity.ok(response);
    }
}
