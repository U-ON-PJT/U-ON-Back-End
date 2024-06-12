package com.uon.message.model.service;

import com.uon.message.dto.Message;

import java.util.List;

public interface MessageService {
    public List<Message> selectMessage(Message message);
    public Message findById(int messageId);
    public int sendMessage(Message message);
    public int deleteMessage(int messageId);
}
