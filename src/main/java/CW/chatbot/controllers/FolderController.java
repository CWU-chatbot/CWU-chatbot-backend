package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.changefolder.ChangeFolderReqDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderResponseDTO;
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
    public ResponseEntity<LoadFolderResponseDTO> loadFolders(@RequestHeader("Authorization") String token) {
        return folderService.loadFolders(token);
    }

    @PostMapping(value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> changeFolder(@RequestBody ChangeFolderReqDTO request) {
        return ResponseEntity.ok("Change endpoint hit");
    }

    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteFolder(@RequestParam("folderId") int folderId) {
        return ResponseEntity.ok("Delete endpoint hit");
    }
}
