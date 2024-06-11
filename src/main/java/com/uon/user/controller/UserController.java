package com.uon.user.controller;

import com.uon.user.model.service.UserService;
import com.uon.util.JWTUtil;
import com.uon.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;

    // 회원가입
    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {
        int result = userService.register(user);
        if (result != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근 - id는 unique한 값");
        }
        return ResponseEntity.ok(result);
    }

    // id 중복 확인
    @GetMapping("/exist/{userId}")
    public ResponseEntity<?> idExist(@PathVariable("userId") String userId) {
        int isDup = userService.idExist(userId);
        // id가 중복일 때
        if (isDup == 1)	return ResponseEntity.status(HttpStatus.CONFLICT).body("id 중복!");

        return ResponseEntity.ok("생성가능!");
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        String token = userService.login(user);
        System.out.println(token);

        if(token == null ) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");

        return ResponseEntity.ok(token);
    }

    // 회원 탈퇴
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String tokenHeader, @PathVariable("userId") String userId) {
        String id = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        if(!id.equals(userId)) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");

        int result = userService.deleteUser(userId);
        if (result == 0) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(result);
    }


}
