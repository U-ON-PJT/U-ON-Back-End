package com.uon.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class Board {
    private int boardId;
    private String userId;
    private int type;
    private String title;
    private String content;
    private String createTime;
}
