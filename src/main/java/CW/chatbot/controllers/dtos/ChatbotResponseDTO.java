package CW.chatbot.controllers.dtos;

public class ChatbotResponseDTO {

    private int status; // 상태

    private int result_code; //결과 코드

    private String result_message; // 결과 메시지

    private String data; //데이터

    public ChatbotResponseDTO() {
    }

    // Getter and setter methods
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getResultCode() {
        return result_code;
    }

    public void setResultCode(int resultCode) {
        this.result_code = resultCode;
    }

    public String getResultMessage() {
        return result_message;
    }

    public void setResultMessage(String resultMessage) {
        this.result_message = resultMessage;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}