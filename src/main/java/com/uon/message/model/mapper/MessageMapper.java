package com.uon.message.model.mapper;

import com.uon.message.dto.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface MessageMapper {
    public List<Message> selectMessage(Message message);
    public Message findById(int messageId);
    public int sendMessage(Message message);
    public int deleteMessage(int messageId);
}
