package com.uon.message.controller;

import com.uon.message.model.service.MessageService;
import com.uon.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;
    private final JWTUtil jwtUtil;

    // 쪽지 조회 (받은, 보낸 쪽지함 list)

    // 쪽지 상세 조회 (받은 쪽지함에서, 처음 읽었을 땐 읽음 처리 하기)

    // 쪽지 보내기

    // 쪽지 삭제
}
