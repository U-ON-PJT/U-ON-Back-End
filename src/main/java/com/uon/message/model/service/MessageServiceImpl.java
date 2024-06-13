package com.uon.message.model.service;

import com.uon.message.dto.Message;
import com.uon.message.model.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
    private final MessageMapper messageMapper;
    @Override
    public List<Message> selectMessage(String userId, int type) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("type", type);

        return messageMapper.selectMessage(param);
    }

    @Override
    @Transactional
    public Message findById(int messageId, String userId) {
        Message receiveMessage = messageMapper.isReceiver(messageId);

        if(userId.equals(receiveMessage.getReceiverId()) && receiveMessage.getIsRead() == 0) messageMapper.firstRead(messageId);

        return messageMapper.findById(messageId);
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
