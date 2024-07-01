package com.uon.message.model.mapper;

import com.uon.message.dto.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MessageMapper {
    public List<Message> selectMessage(Map<String, Object> param);
    int totalRow(Map<String, Object> param);
    int isReadCount(Map<String, Object> param);
    public Message isReceiver(int messageId);
    public int firstRead(int messageId);
    public Message findById(int messageId);
    public int sendMessage(Message message);
    public int deleteMessage(int messageId);
    public int senderDelete(int messageId);
    public int receiverDelete(int messageId);
}
