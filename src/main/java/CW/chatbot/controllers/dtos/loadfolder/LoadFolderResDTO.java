package CW.chatbot.controllers.dtos.loadfolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoadFolderResDTO {
    private int result_code;
    private String result_message;
    private LoadFolderDataDTO data;

    public LoadFolderResDTO(int result_code, String result_message, LoadFolderDataDTO dataList) {
        this.result_code = result_code;
        this.result_message = result_message;
        this.data = dataList;
    }
}
