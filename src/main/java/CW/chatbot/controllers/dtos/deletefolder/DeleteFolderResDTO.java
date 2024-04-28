package CW.chatbot.controllers.dtos.deletefolder;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeleteFolderResDTO {
    private int result_code;
    private String result_message;
    private String result;

    public DeleteFolderResDTO(int result_code, String result_message, String result) {
        this.result_code = result_code;
        this.result_message = result_message;
        this.result = result;
    }
}
