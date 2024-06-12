package com.uon.matching.controller;

import com.uon.matching.dto.Activity;
import com.uon.matching.model.service.MatchingService;
import com.uon.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/activities")
@Slf4j
public class MatchingController {

    private final MatchingService matchingService;
    private final JWTUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> insertMatchingRoom(@RequestBody Activity activity,
                                                @RequestHeader("Authorization") String tokenHeader) {
        String tokenUserId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        activity.setUserId(tokenUserId);
        int isSuccess = matchingService.insertMatchingRoom(activity);

        if (isSuccess == 1) {
            return ResponseEntity.status(200).body("SUCCESS! CREATE MATCHING ROOM");
        }
        return ResponseEntity.status(400).body("FAILD! CREATE MATCHING ROOM");
    }

    @PutMapping
    public ResponseEntity<?> updateMatchingRoom(@RequestBody Activity activity,
                                                @RequestHeader("Authorization") String tokenHeader) {
        int isSuccess = matchingService.updateMatchingRoom(activity);

        if (isSuccess == 1) {
            return ResponseEntity.status(200).body("SUCCESS! UPDATE MATCHING ROOM");
        }
        return ResponseEntity.status(400).body("FAILD! UPDATE MATCHING ROOM");
    }

    @GetMapping
    public ResponseEntity<?> selectAllMatchingRoom() {
        List<Activity> activityList = matchingService.selectAllMatchingRoom();
        System.out.println(activityList);
        if (activityList != null) {
            return ResponseEntity.status(200).body(activityList);
        }

        return ResponseEntity.status(400).body("Faild! selectAllMatchingRoom()");
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> selectMatchingRoomOfType(@PathVariable("type") int type) {
        List<Activity> activityListOfType = matchingService.selectMatchingRoomOfType(type);

        if (activityListOfType.isEmpty()) {
            return ResponseEntity.status(200).body("Empty Matching Room Of Type");
        } else if (activityListOfType != null) {
            return ResponseEntity.status(200).body(activityListOfType);
        }
        return ResponseEntity.status(400).body("Faild! selectMatchingRoomOfType()");
    }

    @GetMapping("/detail/{activityId}")
    public ResponseEntity<?> selectMatchingRoom(@PathVariable("activityId") int activityId) {
        Activity activityInfo = matchingService.selectMatchingRoom(activityId);

        if (activityInfo != null) {
            return ResponseEntity.status(200).body(activityInfo);
        }
        return ResponseEntity.status(400).body("Faild! selectMatchingRoom()");
    }
}
