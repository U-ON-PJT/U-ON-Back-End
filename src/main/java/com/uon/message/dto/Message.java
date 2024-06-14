package com.uon.message.dto;

import lombok.Data;

@Data
public class Message {
    private int messageId;
    private String senderId;
    private String receiverId;
    private String title;
    private String content;
    private String sendTime;
    private int isRead;
    private int type; // 송수신 구분
}
