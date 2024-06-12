package com.uon.board.dto;

import lombok.Data;

@Data
public class BoardImage {
    private String saveName;
    private int boardId;
    private String imageName;
    private String imagePath;
}
