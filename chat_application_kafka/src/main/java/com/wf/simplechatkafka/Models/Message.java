package com.wf.simplechatkafka.Models;

import lombok.*;

@Data @Builder @AllArgsConstructor @NoArgsConstructor @ToString
public class Message {
    private String sender;
    private String content;
    private String timestamp;
}
