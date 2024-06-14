package com.uon.message.model.service;

import com.uon.message.dto.Message;
import com.uon.message.dto.MessagePaginationResponse;
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
    @Transactional
    public MessagePaginationResponse selectMessage(String userId, int type, int size, int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("type", type);
        param.put("size", size);
        param.put("offset", (page-1)*size);

        List<Message> messages = messageMapper.selectMessage(param);

        MessagePaginationResponse resp = new MessagePaginationResponse();
        resp.setMessageList(messages);
        int totalRow = messageMapper.totalRow(param);
        int totalPages = ((totalRow-1)/size)+1;
        resp.setTotalPages(totalPages);
        resp.setSize(size);
        resp.setPage(page);

        if(type == 1) resp.setCount(messageMapper.isReadCount(param));

        return resp;
    }

    @Override
    @Transactional
    public Message findById(int messageId, String userId) {
        Message receiveMessage = messageMapper.isReceiver(messageId);

        if(userId.equals(receiveMessage.getReceiverId()) && receiveMessage.getIsRead() == 0) messageMapper.firstRead(messageId);

        return messageMapper.findById(messageId);
    }

    @Override
    @Transactional
    public int sendMessage(Message message) {
        String senderId = message.getSenderId();
        String receiverId = message.getReceiverId();

        int result = messageMapper.sendMessage(message);

        if(result == 0) return 0;

        message.setSenderId(receiverId);
        message.setReceiverId(senderId);

        return messageMapper.sendMessage(message);
    }

    @Override
    public int deleteMessage(int messageId) {

        return messageMapper.deleteMessage(messageId);
    }
}
