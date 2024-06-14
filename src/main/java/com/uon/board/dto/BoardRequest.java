package com.uon.board.dto;

import lombok.Data;

import java.util.List;


@Data
public class BoardRequest {
    private Board board;
    private List<String> imageUrls;
}
