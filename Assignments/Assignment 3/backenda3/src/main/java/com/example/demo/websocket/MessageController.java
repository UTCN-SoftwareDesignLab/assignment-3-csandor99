package com.example.demo.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class MessageController {

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public MessageHolder getMessage(ConsultationNotification notification) throws Exception{
        Thread.sleep(1000);
        return new MessageHolder("New consultation on: "
                + HtmlUtils.htmlEscape(notification.getDate())
                + "\nPatient: " + HtmlUtils.htmlEscape(notification.getPatient()));
    }
}
