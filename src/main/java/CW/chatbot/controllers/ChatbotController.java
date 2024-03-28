package CW.chatbot.controllers;


import CW.chatbot.controllers.dtos.ChatbotResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatbotController {

    @GetMapping("/chat")
    public ChatbotResponseDTO getResponse() {
        ChatbotResponseDTO response = new ChatbotResponseDTO();

        response.setStatus(1);

        response.setResultCode(200);

        response.setResultMessage("Success");

        response.setData("hello!");

        return response;
    }
}
