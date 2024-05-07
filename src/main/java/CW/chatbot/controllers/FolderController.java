package CW.chatbot.controllers;

import CW.chatbot.controllers.dtos.changefolder.ChangeFolderDataDTO;
import CW.chatbot.controllers.dtos.changefolder.ChangeFolderReqDTO;
import CW.chatbot.controllers.dtos.changefolder.ChangeFolderResDTO;
import CW.chatbot.controllers.dtos.createfolder.CreateFolderReqDTO;
import CW.chatbot.controllers.dtos.createfolder.CreateFolderResDTO;
import CW.chatbot.controllers.dtos.deletefolder.DeleteFolderReqDTO;
import CW.chatbot.controllers.dtos.deletefolder.DeleteFolderResDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderDataDTO;
import CW.chatbot.controllers.dtos.loadfolder.LoadFolderResDTO;
import CW.chatbot.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 폴더 관련 요청을 처리하는 컨트롤러 클래스.
 * 이 클래스는 폴더 생성, 로드, 변경, 삭제 등 폴더 관련 기능을 제공하는 REST API 엔드포인트를 정의합니다.
 * 클라이언트로부터 폴더 관련 요청을 받아 서비스 계층으로 전달하고, 처리 결과를 응답으로 반환합니다.
 * <p>
 * 주요 기능:
 * 1. **폴더 생성**: 클라이언트로부터 폴더 내용을 받아 새 폴더를 생성합니다.
 * 2. **폴더 로드**: 지정된 사용자의 폴더 목록을 로드합니다.
 * 3. **폴더 변경**: 특정 폴더의 내용을 변경하는 요청을 처리합니다.
 * 4. **폴더 삭제**: 특정 폴더를 삭제합니다.
 * <p>
 * 각 메서드 설명:
 * - createFolders: 헤더에서 받은 'Authorization' 토큰과 요청 본문에서 받은 폴더 내용을 기반으로 새 폴더를 생성합니다. 폴더 내용이 비어있지 않아야 합니다.
 * - loadFolders: 'Authorization' 토큰을 사용하여 특정 사용자의 폴더 데이터를 로드합니다. 사용자에게 폴더가 없으면 오류를 반환합니다.
 * - changeFolder: 요청 본문에서 받은 폴더 ID를 사용하여 해당 폴더의 내용을 변경합니다. 폴더 ID는 비어있지 않아야 합니다.
 * - deleteFolder: 요청 본문에서 받은 폴더 ID로 폴더를 삭제합니다. 폴더 ID는 비어있지 않아야 합니다.
 * <p>
 * 예외 처리:
 * - 각 메서드는 입력 값의 유효성 검사를 수행하고, 유효하지 않은 경우 적절한 HTTP 상태 코드와 메시지를 반환합니다.
 * - 데이터베이스 작업 중 발생할 수 있는 예외에 대해 내부 서버 오류를 반환합니다.
 * <p>
 * 모든 폴더 관련 요청은 이 경로를 통해 이 클래스로 라우팅됩니다.
 */

@RestController
@RequestMapping(value = "/folder")
public class FolderController {

    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    // 폴더 생성
    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createFolders(@RequestHeader("Authorization") String token, @RequestBody CreateFolderReqDTO CreateFolderReqDTO) {
        try {
            if (CreateFolderReqDTO.getFolderContent() == null || CreateFolderReqDTO.getFolderContent().trim().isEmpty()) {
                return ResponseEntity.badRequest().body(new CreateFolderResDTO(400, "Folder content cannot be empty", "Fail"));
            }
            int folderId = folderService.createFolders(token, CreateFolderReqDTO.getFolderContent());
            return ResponseEntity.ok(new CreateFolderResDTO(200, "Success", Integer.toString(folderId)));  // Now returning folder ID
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new CreateFolderResDTO(500, "Internal Server Error", "Fail"));
        }
    }

    // 폴더 로드
    @PostMapping(value = "/load", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> loadFolders(@RequestHeader("Authorization") String token) {
        try {
            LoadFolderDataDTO data = folderService.loadFolders(token);
            if (data == null) {
                return ResponseEntity.badRequest().body(new LoadFolderResDTO(400, "User folder does not exist.", null));
            }
            return ResponseEntity.ok(new LoadFolderResDTO(200, "Success", data));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new LoadFolderResDTO(500, "Internal Server Error", null));
        }
    }

    // 폴더 변경
    @PostMapping(value = "/change", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> changeFolder(@RequestBody ChangeFolderReqDTO changeFolderReqDTO) {
        try {
            if (changeFolderReqDTO.getFolderId() == null) {
                return ResponseEntity.badRequest().body(new ChangeFolderResDTO(400, "folderId cannot be empty", null));
            }
            ChangeFolderDataDTO data = folderService.changeFolders(changeFolderReqDTO.getFolderId());
            return ResponseEntity.ok(new ChangeFolderResDTO(200, "Success", data));
        } catch (Exception e) {
            // 예외 메시지를 클라이언트의 요청 형식에 맞추어 JSON 구조로 반환
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ChangeFolderResDTO(404, e.getMessage(), null));
        }
    }

    // 폴더 삭제
    @PostMapping(value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> deleteFolder(@RequestBody DeleteFolderReqDTO deleteFolderReqDTO) {
        try {
            if (deleteFolderReqDTO.getFolderId() == null) {
                return ResponseEntity.badRequest().body(new DeleteFolderResDTO(400, "Folder ID cannot be empty", "Fail"));
            }
            String result = folderService.DeleteFolders(deleteFolderReqDTO.getFolderId());
            return ResponseEntity.ok(new DeleteFolderResDTO(200, "Success", result));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new DeleteFolderResDTO(404, e.getMessage(), "Fail"));
        }
    }
}
