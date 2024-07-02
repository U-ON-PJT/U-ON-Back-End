package com.uon.message.model.service;

import com.uon.message.dto.Message;
import com.uon.message.dto.MessagePaginationResponse;
import com.uon.message.model.mapper.MessageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{
    private final MessageMapper messageMapper;
    @Override
    @Transactional
    public MessagePaginationResponse selectMessage(String userId, int size, int page) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
//        param.put("type", type);
        param.put("size", size);
        param.put("offset", (page-1)*size);

        int unReadCount = 0;
        List<Message> messages = messageMapper.selectMessage(param);

        for(Message message : messages){
            if(message.getReceiverId().equals(userId)) {
                if(message.getIsRead() == 0) unReadCount++;
            }
        }

        MessagePaginationResponse resp = new MessagePaginationResponse();
        resp.setMessageList(messages);
        int totalRow = messageMapper.totalRow(param);
        int totalPages = ((totalRow-1)/size)+1;
        resp.setTotalPages(totalPages);
        resp.setSize(size);
        resp.setPage(page);

        resp.setCount(unReadCount);

//        if(type == 1) resp.setCount(messageMapper.isReadCount(param));

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
    public int sendMessage(Message message) {
        try {
            return messageMapper.sendMessage(message);
        }
        catch (Exception e) {
            System.err.println("Foreign key constraint violation: " + e.getMessage());
            return 0;
        }
    }

    @Override
    public int deleteMessage(int messageId, String userId) {
        Message message = messageMapper.findById(messageId);

        if(message.getIsDelete() != 0) return messageMapper.deleteMessage(messageId);

        if(message.getSenderId().equals(userId)) return messageMapper.senderDelete(messageId);
        else if(message.getReceiverId().equals(userId)) return messageMapper.receiverDelete(messageId);

        return 0;

    }
}
