package CW.chatbot.controllers.dtos.changefolder;

import CW.chatbot.entities.LOGS;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChangeFolderDataDTO {
    private List<LOGS> dataList;

    public ChangeFolderDataDTO(List<LOGS> dataList) {
        this.dataList = dataList;
    }
}
