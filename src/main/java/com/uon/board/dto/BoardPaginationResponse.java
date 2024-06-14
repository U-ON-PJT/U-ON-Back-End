package com.uon.board.dto;

import lombok.Data;

import java.util.List;

@Data
public class BoardPaginationResponse {
    private List<Board> boardList;
    private int size;
    private int page;
    private int totalPages;
}
