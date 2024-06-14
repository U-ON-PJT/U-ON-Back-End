package com.uon.board.model.service;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardRequest;

import java.util.List;


public interface BoardService {
    int insert(BoardRequest board);
    List<Board> getBoard(int type);

    BoardRequest getBoardById(int boardId);

    int deleteBoard(int boardId);
    int update(BoardRequest board);
}
