package com.uon.message.model.service;

import com.uon.message.dto.Message;

import java.util.List;

public class MessageServiceImpl implements MessageService{
    @Override
    public List<Message> selectMessage(Message message) {
        return null;
    }

    @Override
    public Message findById(int messageId) {
        return null;
    }

    @Override
    public int sendMessage(Message message) {
        return 0;
    }

    @Override
    public int deleteMessage(int messageId) {
        return 0;
    }
}
