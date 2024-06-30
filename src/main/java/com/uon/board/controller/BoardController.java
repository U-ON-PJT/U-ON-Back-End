package com.uon.board.controller;

import com.uon.board.dto.Board;
import com.uon.board.dto.BoardPaginationResponse;
import com.uon.board.dto.BoardRequest;
import com.uon.board.model.service.BoardService;
import com.uon.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {
    private final BoardService boardService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody BoardRequest boardRequest, @RequestHeader("Authorization") String tokenHeader) {
        String role = jwtUtil.getRoleFromToken(tokenHeader.substring(7));
        if(role.equals("0")) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");

        int result = boardService.insert(boardRequest);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> getBoard(@PathVariable("type") int type,
    @RequestParam(value = "size", defaultValue = "10") int size,
    @RequestParam(value = "page", defaultValue = "1") int page) {
        BoardPaginationResponse response = boardService.getBoard(type, size, page);

        return ResponseEntity.ok(response);
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
