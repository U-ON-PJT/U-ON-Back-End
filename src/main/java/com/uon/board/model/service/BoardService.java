package com.uon.board.model.service;

import com.uon.board.dto.Board;

import java.util.List;


public interface BoardService {
    int insert(Board board);
    List<Board> getBoard(int type);

    Board getBoardById(int boardId);

}
