package com.wf.simplechatkafka.listener;

import com.wf.simplechatkafka.Models.Message;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MessageListener {

    private SimpMessagingTemplate template;

    @KafkaListener(
            topics = "r1",
            groupId = "1001"
    )
    public void listen( Message message) {
        template.convertAndSend("/start/initial", message);
    }
}
