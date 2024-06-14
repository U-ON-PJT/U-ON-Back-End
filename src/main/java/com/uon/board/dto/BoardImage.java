package com.uon.board.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Data
public class BoardImage {
    private int boardId;
    private String imageUrl;
}
