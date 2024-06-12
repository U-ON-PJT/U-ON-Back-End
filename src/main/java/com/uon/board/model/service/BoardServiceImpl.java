package com.uon.board.model.service;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardImage;
import com.uon.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardMapper boardMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE) // 동시성 오류 해결 위해 serializable 적용
    @Override
    public int insert(Board boardInfo){
        int cnt = boardMapper.insert(boardInfo);
        int boardId = boardMapper.getBoardId();
        List<BoardImage> boardImages = boardInfo.getBoardImages();
        for(BoardImage boardImg : boardImages) {
            boardImg.setBoardId(boardId);
        }

        boardMapper.insertImages(boardImages);

        return cnt;
    }

    @Override
    public List<Board> getBoard(int type) {
        return boardMapper.getBoard(type);
    }

    @Override
    public Board getBoardById(int boardId) {
        List<BoardImage> images = boardMapper.getBoardImages(boardId);
        Board board = boardMapper.getBoardById(boardId);
        board.setBoardImages(images);
        return board;
    }



}
