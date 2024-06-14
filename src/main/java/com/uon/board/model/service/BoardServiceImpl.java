package com.uon.board.model.service;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardImage;
import com.uon.board.dto.BoardRequest;
import com.uon.board.model.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardMapper boardMapper;

    @Transactional(isolation = Isolation.SERIALIZABLE) // 동시성 오류 해결 위해 serializable 적용
    @Override
    public int insert(BoardRequest boardInfo){
        Board board = boardInfo.getBoard();

        int cnt = boardMapper.insert(board);
        int boardId = boardMapper.getBoardId();

        List<String> images = boardInfo.getImageUrls();
        System.out.println(boardId);
        List<BoardImage> boardImages = new ArrayList<>();
        for(String imageUrl : images) {
            BoardImage boardImage = new BoardImage();
            boardImage.setBoardId(boardId);
            boardImage.setImageUrl(imageUrl);
            boardImages.add(boardImage);
        }

        if(boardImages.size() != 0) {
            boardMapper.insertImages(boardImages);
        }

        return cnt;
    }

    @Override
    public List<Board> getBoard(int type) {
        return boardMapper.getBoard(type);
    }

    @Override
    public BoardRequest getBoardById(int boardId) {
        List<BoardImage> images = boardMapper.getBoardImages(boardId);
        List<String> imageUrls = new ArrayList<>();
        for(BoardImage boardImage : images){
            imageUrls.add(boardImage.getImageUrl());
        }
        Board board = boardMapper.getBoardById(boardId);
        BoardRequest boardRequest = new BoardRequest();
        boardRequest.setBoard(board);
        boardRequest.setImageUrls(imageUrls);
        return boardRequest;
    }

    @Override
    public int deleteBoard(int boardId) {
        return boardMapper.delete(boardId);
    }

    @Override
    public int update(BoardRequest boardInfo) {
        Board board = boardInfo.getBoard();

        int cnt = boardMapper.update(board);
        int boardId = boardInfo.getBoard().getBoardId();

        List<String> images = boardInfo.getImageUrls();

        List<BoardImage> boardImages = new ArrayList<>();
        for(String imageUrl : images) {
            BoardImage boardImage = new BoardImage();
            boardImage.setBoardId(boardId);
            boardImage.setImageUrl(imageUrl);
            boardImages.add(boardImage);
        }

        boardMapper.deleteImages(boardId);
        if(boardImages.size() != 0) {
            boardMapper.insertImages(boardImages);
        }

        return cnt;
    }


}
