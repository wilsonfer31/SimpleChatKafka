package com.wf.simplechatkafka.controller;

import com.wf.simplechatkafka.Models.Message;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@RestController
public class ChatController {


    private KafkaTemplate<String, Message> kafkaTemplate;

    public ChatController(KafkaTemplate<String, Message> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping(value = "/api/send")
    public void sendMessage(@RequestBody Message message) {
        message.setTimestamp(LocalDateTime.now().toString());
        try {
            kafkaTemplate.send("r1", message).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @MessageMapping("/resume")
    @SendTo("/start/initial")
        public void broadcastGroupMessage( @Payload Message message) {
        message.setTimestamp(LocalDateTime.now().toString());
        kafkaTemplate.send("r1", message);

    }
}