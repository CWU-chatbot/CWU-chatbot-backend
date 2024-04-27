package CW.chatbot.services;

import CW.chatbot.controllers.dtos.changefolder.ChangeFolderDataDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderDataDTO;
import CW.chatbot.entities.FOLERS;
import CW.chatbot.entities.LOGS;
import CW.chatbot.provider.JwtTokenProvider;
import CW.chatbot.repositories.FoldersRepository;
import CW.chatbot.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FolderService {

    private final JwtTokenProvider jwtTokenProvider;
    private final FoldersRepository foldersRepository;
    private final LogsRepository logsRepository;

    @Autowired
    public FolderService(JwtTokenProvider jwtTokenProvider, FoldersRepository foldersRepository, LogsRepository logsRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.foldersRepository = foldersRepository;
        this.logsRepository = logsRepository;
    }

    private String GetUserId(String token) {
        return jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", "").trim());
    }

    public LoadFolderDataDTO loadFolders(String token) {
        String userId = GetUserId(token);
        List<FOLERS> dataList = foldersRepository.findByUserId(userId);

        return new LoadFolderDataDTO(dataList);
    }

    public ChangeFolderDataDTO changeFolders(int folderId) {
        List<LOGS> dataList = logsRepository.findByFolderId(folderId);

        return new ChangeFolderDataDTO(dataList);
    }
}
