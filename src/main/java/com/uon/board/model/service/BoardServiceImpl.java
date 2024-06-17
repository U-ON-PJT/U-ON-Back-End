package com.uon.board.model.service;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardImage;
import com.uon.board.dto.BoardPaginationResponse;
import com.uon.board.dto.BoardRequest;
import com.uon.board.model.mapper.BoardMapper;
import com.uon.message.dto.Message;
import com.uon.message.dto.MessagePaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public BoardPaginationResponse getBoard(int type, int size, int page) {
        Map<String, Object> param = new HashMap<>() ;

        param.put("type", type);
        param.put("size", size);
        param.put("offset", (page-1)*size);

        List<Board> boardList = boardMapper.getBoard(param);

        BoardPaginationResponse resp = new BoardPaginationResponse();
        resp.setBoardList(boardList);
        int totalRow = boardMapper.totalRow(param);
        int totalPages = ((totalRow-1)/size)+1;
        resp.setTotalPages(totalPages);
        resp.setSize(size);
        resp.setPage(page);


        return resp;
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
