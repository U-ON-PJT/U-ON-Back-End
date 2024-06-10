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

    @GetMapping("/exist/{userId}")
    public ResponseEntity<?> idExist(@PathVariable("userId") String userId) {
        int isDup = userService.idExist(userId);
        // id가 중복일 때
        if (isDup == 1)	return ResponseEntity.status(HttpStatus.CONFLICT).body("id 중복!");

        return ResponseEntity.ok("생성가능!");
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@RequestBody User user) {

        int result = userService.register(user);
        if (result != 1) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근");
        }
        return ResponseEntity.ok(result);
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        String token = userService.login(user);
        System.out.println(token);

        if(token == null ) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("아이디 또는 비밀번호가 잘못되었습니다.");

        return ResponseEntity.ok(token);
    }
}
