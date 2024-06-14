package com.uon.board.controller;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardRequest;
import com.uon.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody BoardRequest boardRequest) {
        int result = boardService.insert(boardRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getBoard(@PathVariable("type") int type) {
        List<Board> boardList = boardService.getBoard(type);


        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/detail/{boardId}")
    public ResponseEntity<?> getBoardDetail(@PathVariable("boardId") int boardId) {
        BoardRequest board = boardService.getBoardById(boardId);


        return ResponseEntity.ok(board);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<?> delete(@PathVariable("boardId") int boardId) {
        int cnt = boardService.deleteBoard(boardId);

        return ResponseEntity.ok(cnt);
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody BoardRequest boardRequest) {
        int result = boardService.update(boardRequest);

        return ResponseEntity.ok(result);
    }


}
