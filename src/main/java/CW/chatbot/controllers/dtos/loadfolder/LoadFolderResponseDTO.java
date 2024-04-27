package CW.chatbot.controllers.dtos.loadfolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoadFolderResponseDTO {
    private int result_code;
    private String result_message;
    private LoadFolderDataDTO data;

    public LoadFolderResponseDTO(int result_code, String result_message, LoadFolderDataDTO dataList) {
        this.result_code = result_code;
        this.result_message = result_message;
        this.data = dataList;
    }
}
