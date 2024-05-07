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

/**
 * 폴더 관련 작업을 처리하는 서비스 클래스.
 * 이 클래스는 폴더 생성, 로드, 변경, 삭제 등의 기능을 제공하며, 사용자 인증 토큰을 처리하여 사용자 식별을 관리합니다.
 * <p>
 * 주요 기능:
 * - 폴더 생성: 사용자 토큰을 해석하여 폴더를 생성합니다.
 * - 폴더 로드: 주어진 사용자 토큰으로 사용자의 폴더 데이터를 로드합니다.
 * - 폴더 변경: 폴더 ID를 기반으로 해당 폴더의 로그 데이터를 변경합니다.
 * - 폴더 삭제: 특정 폴더를 삭제합니다.
 * <p>
 * 구성 요소:
 * - JwtTokenProvider: JWT 토큰에서 사용자 ID를 추출하는 데 사용됩니다.
 * - FoldersRepository: 폴더 데이터에 대한 데이터베이스 작업을 수행합니다.
 * - LogsRepository: 폴더의 로그 데이터에 대한 데이터베이스 작업을 수행합니다.
 * <p>
 * 메소드 설명:
 * - GetUserId(String token): 주어진 JWT 토큰에서 사용자 ID를 추출합니다.
 * - createFolders(String token, String content): 주어진 토큰과 폴더 내용으로 새 폴더를 생성합니다.
 * - loadFolders(String token): 토큰을 해석하여 해당 사용자의 모든 폴더를 로드합니다.
 * - changeFolders(int folderId): 주어진 폴더 ID에 대해 폴더 내 로그 데이터를 조회합니다.
 * - DeleteFolders(int folderId): 주어진 폴더 ID를 가진 폴더를 삭제합니다.
 * <p>
 * 예외 처리:
 * - 각 메소드는 작업 수행 중 발생할 수 있는 예외를 처리하고 적절한 오류 메시지를 반환합니다.
 * 예외는 주로 사용자 인증 실패, 데이터 불일치 등에서 발생합니다.
 * <p>
 * 사용 예:
 * - 이 서비스는 폴더 관련 API 요청을 처리하는 컨트롤러에서 호출됩니다. 각 API 엔드포인트는 서비스 메소드를 통해
 * 데이터를 처리하고 결과를 반환합니다.
 */

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

    // 사용자 ID 가져오기
    private String GetUserId(String token) throws Exception {
        try {
            return jwtTokenProvider.getUserIdFromToken(token.replace("Bearer ", "").trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

    // 폴더 생성
    public String createFolders(String token, String content) throws Exception {
        String userId = GetUserId(token);
        FOLERS userRequest = new FOLERS(userId, content);
        foldersRepository.save(userRequest);
        return "Success";
    }

    // 폴더 로드
    public LoadFolderDataDTO loadFolders(String token) throws Exception {
        String userId = GetUserId(token);
        List<FOLERS> dataList = foldersRepository.findByUserId(userId);
        if (dataList == null || dataList.isEmpty()) {
            throw new Exception("No folders found for this user");
        }
        return new LoadFolderDataDTO(dataList);
    }

    // 폴더 변경
    public ChangeFolderDataDTO changeFolders(int folderId) throws Exception {
        List<LOGS> dataList = logsRepository.findByFolderId(folderId);
        if (dataList == null || dataList.isEmpty()) {
            throw new Exception("No logs found for this folder");
        }
        return new ChangeFolderDataDTO(dataList);
    }

    // 폴더 삭제
    public String DeleteFolders(int folderId) throws Exception {
        foldersRepository.deleteByFolderId(folderId);
        return "Success";
    }
}
