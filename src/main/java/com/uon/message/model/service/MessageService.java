package com.uon.message.model.service;

import com.uon.message.dto.Message;
import com.uon.message.dto.MessagePaginationResponse;

import java.util.List;

public interface MessageService {
    public MessagePaginationResponse selectMessage(String userId, int size, int page);
    public Message findById(int messageId, String userId);
    public int sendMessage(Message message);
    public int deleteMessage(int messageId, String userId);
}
