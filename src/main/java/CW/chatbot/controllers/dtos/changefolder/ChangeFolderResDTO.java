package CW.chatbot.controllers.dtos.changefolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ChangeFolderResDTO {
    private int result_code;
    private String result_message;
    private ChangeFolderDataDTO data;

    public ChangeFolderResDTO(int result_code, String result_message, ChangeFolderDataDTO dataList) {
        this.result_code = result_code;
        this.result_message = result_message;
        this.data = dataList;
    }
}
