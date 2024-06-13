package com.uon.message.model.service;

import com.uon.message.dto.Message;

import java.util.List;

public interface MessageService {
    public List<Message> selectMessage(String userId, int type);
    public Message findById(int messageId, String userId);
    public int sendMessage(Message message);
    public int deleteMessage(int messageId);
}
