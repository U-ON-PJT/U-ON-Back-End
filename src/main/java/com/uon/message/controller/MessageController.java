package com.uon.message.controller;

import com.uon.message.dto.Message;
import com.uon.message.dto.MessagePaginationResponse;
import com.uon.message.model.service.MessageService;
import com.uon.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final JWTUtil jwtUtil;

    // 쪽지 조회 (받은, 보낸 쪽지함 list)
    @GetMapping("/{type}")
    public ResponseEntity<?> selectAll(@RequestHeader("Authorization") String tokenHeader,
                                       @PathVariable("type") int type,
                                       @RequestParam(value = "size", defaultValue = "10") int size,
                                       @RequestParam(value = "page", defaultValue = "1") int page) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        MessagePaginationResponse resp = messageService.selectMessage(userId, type, size, page);

        return ResponseEntity.ok(resp);
    }

    // 쪽지 상세 조회 (받은 쪽지함에서, 처음 읽었을 땐 읽음 처리 하기)
    @GetMapping("detail/{boardId}")
    public ResponseEntity<?> findById(@RequestHeader("Authorization") String tokenHeader, @PathVariable("boardId") int boardId) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        Message message = messageService.findById(boardId, userId);

        if(message == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("실패");

        return ResponseEntity.ok(message);
    }

    // 쪽지 보내기
    @PostMapping
    public ResponseEntity<?> regist(@RequestHeader("Authorization") String tokenHeader, @RequestBody Message message) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        message.setSenderId(userId);
        
        int result = messageService.sendMessage(message);

        System.out.println(result);

        if(result == 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("전송 실패");
        
        return ResponseEntity.ok(result);
    }

    // 쪽지 삭제
    @DeleteMapping("/{messageId}")
    public ResponseEntity<?> deleteMessage(@RequestHeader("Authorization") String tokenHeader, @PathVariable("messageId") int messageId) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        int result = messageService.deleteMessage(messageId, userId);

        if(result == 0) ResponseEntity.status(HttpStatus.NOT_FOUND).body("잘못된 요청");

        return ResponseEntity.ok(result);
    }
}
