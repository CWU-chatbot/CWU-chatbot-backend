package CW.chatbot.controllers.dtos.loadfolder;

import CW.chatbot.entities.FOLERS;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class LoadFolderDataDTO {
    private List<FOLERS> dataList;

    public LoadFolderDataDTO(List<FOLERS> dataList) {
        this.dataList = dataList;
    }
}
