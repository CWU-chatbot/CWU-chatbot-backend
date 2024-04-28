package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.changefolder.ChangeFolderDataDTO;
import CW.chatbot.controllers.dtos.changefolder.ChangeFolderReqDTO;
import CW.chatbot.controllers.dtos.changefolder.ChangeFolderResDTO;
import CW.chatbot.controllers.dtos.deletefolder.DeleteFolderReqDTO;
import CW.chatbot.controllers.dtos.deletefolder.DeleteFolderResDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderDataDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderResDTO;
import CW.chatbot.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/folder")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping(value = "/load", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoadFolderResDTO> loadFolders(@RequestHeader("Authorization") String token) {
        LoadFolderDataDTO data = folderService.loadFolders(token);
        if (data == null) {
            return ResponseEntity.badRequest().body(new LoadFolderResDTO(400, "User folder does not exist.", null));
        }
        return ResponseEntity.ok(new LoadFolderResDTO(200, "Success", data));
    }

    @PostMapping(value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ChangeFolderResDTO> changeFolder(@RequestBody ChangeFolderReqDTO ChangeFolderReqDTO) {
        if (ChangeFolderReqDTO.getFolderId() == null) {
            return ResponseEntity.badRequest().body(new ChangeFolderResDTO(400, "folderId not be empty", null));
        }
        int folderId = ChangeFolderReqDTO.getFolderId();

        ChangeFolderDataDTO data = folderService.changeFolders(folderId);
        return ResponseEntity.ok(new ChangeFolderResDTO(200, "Success", data));
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DeleteFolderResDTO> deleteFolder(@RequestBody DeleteFolderReqDTO DeleteFolderReqDTO) {
        if (DeleteFolderReqDTO.getFolderId() == null) {
            return ResponseEntity.badRequest().body(new DeleteFolderResDTO(400, "folderId not be empty", "Fail"));
        }
        int folderId = DeleteFolderReqDTO.getFolderId();

        String result = folderService.DeleteFolders(folderId);
        return ResponseEntity.ok(new DeleteFolderResDTO(200, "Success", result));
    }
}
