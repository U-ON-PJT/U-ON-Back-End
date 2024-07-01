package com.uon.user.controller;

import com.uon.user.model.service.UserService;
import com.uon.util.JWTUtil;
import com.uon.user.dto.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        if (result != 1) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근 - id는 unique한 값");

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
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        int result = userService.deleteUser(userId);

        if (result == 0) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 id를 가진 회원은 없음.");

        return ResponseEntity.ok(result);
    }

    // 회원 정보 조회
    @GetMapping("/my-page")
    public ResponseEntity<?> findById(@RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        User user = userService.findById(userId);

        if(user == null) return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 id를 가진 회원은 없음.");

        return ResponseEntity.ok(user);
    }
    @GetMapping("/others")
    public ResponseEntity<?> getUserInfo(String userId) {

        User user = userService.getUserInfo(userId);

        if(user == null) return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("해당 id를 가진 회원은 없음.");

        return ResponseEntity.ok(user);
    }

    // 회원 정보 변경
    @PutMapping("/my-page")
    public ResponseEntity<?> updateUser(@RequestHeader("Authorization") String tokenHeader, @RequestBody User user) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        if(!userId.equals(user.getUserId())) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("잘못된 접근입니다.");
        System.out.println("user: " + user);

        String token = userService.updateUser(user);

        if(token == null ) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("변경 실패");

        return ResponseEntity.ok(token);
    }

    // 비밀번호 변경
    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestHeader("Authorization") String tokenHeader, @RequestBody User user) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        user.setUserId(userId);

        int result = userService.updatePassword(user);

        if (result == 0) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀번호 오류");

        return ResponseEntity.ok(result);
    }

    // 아이디 찾기
    @PostMapping("/user-id")
    public ResponseEntity<?> findId(@RequestBody User user) {
       List<String> userId = userService.getId(user);

        if (userId == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("id 없음");

        return ResponseEntity.ok(userId);
    }
}
