package com.uon.board.model.mapper;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardImage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    int insert (Board board);
    int insertImages(List<BoardImage> boardImageList);
    int getBoardId();

    List<Board> getBoard(int type);
    List<BoardImage> getBoardImages(int boardId);
    Board getBoardById(int boardId);

    int update(Board board);
    int deleteImages(int boardId);
    int updateImages(List<BoardImage> boardImageList);

    int delete(int boardId);

}
