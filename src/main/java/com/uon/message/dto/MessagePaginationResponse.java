package com.uon.message.dto;

import lombok.Data;

import java.util.List;

@Data
public class MessagePaginationResponse {
    private List<Message> messageList;
    private int totalPages;
    private int page;
    private int size;
    private int count;
}
