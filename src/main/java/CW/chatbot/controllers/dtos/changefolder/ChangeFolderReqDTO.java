package CW.chatbot.controllers.dtos.changefolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeFolderReqDTO {
    private int folderId;
    private String newContent;

    public ChangeFolderReqDTO() {
    }

    public ChangeFolderReqDTO(int folderId, String newContent) {
        this.folderId = folderId;
        this.newContent = newContent;
    }
}
