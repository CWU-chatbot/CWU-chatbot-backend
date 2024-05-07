package CW.chatbot.controllers.dtos.createfolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateFolderReqDTO {
    private String folderContent;

    public CreateFolderReqDTO() {
    }
}
