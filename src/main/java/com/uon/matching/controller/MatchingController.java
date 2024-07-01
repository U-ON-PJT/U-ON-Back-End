package com.uon.matching.controller;

import com.uon.matching.dto.Activity;
import com.uon.matching.dto.Participant;
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
    public ResponseEntity<?> selectAllMatchingRoom(@RequestParam(value = "size", defaultValue = "10") int size,
                                                    @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Activity> activityList = matchingService.selectAllMatchingRoom(size, page);
        if (activityList != null) {
            return ResponseEntity.status(200).body(activityList);
        }

        return ResponseEntity.status(400).body("Faild! selectAllMatchingRoom()");
    }

    @GetMapping("/{type}")
    public ResponseEntity<?> selectMatchingRoomOfType(@PathVariable("type") int type,
                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                      @RequestParam(value = "page", defaultValue = "1") int page) {
        List<Activity> activityListOfType = matchingService.selectMatchingRoomOfType(type, size, page);

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

    @GetMapping("/my-matching-room")
    public ResponseEntity<?> selectAllMyMatchingRoom(
                                                @RequestHeader("Authorization") String tokenHeader,
                                                @RequestParam(value = "size", defaultValue = "10") int size,
                                                @RequestParam(value = "page", defaultValue = "1") int page) {
        String tokenUserId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        List<Activity> activityList = matchingService.selectAllMyMatchingRoom(tokenUserId, size, page);
        if (activityList != null) {
            return ResponseEntity.status(200).body(activityList);
        }

        return ResponseEntity.status(400).body("Faild! selectAllMatchingRoom()");
    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<?> deleteMatchingRoom(@PathVariable("activityId") int activityId,
                                                @RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        int isSuccess = matchingService.deleteMatchingRoom(userId, activityId);

        if (isSuccess == 0) {
            return ResponseEntity.status(200).body("Success! Delete Matching Room");
        }
        return ResponseEntity.status(400).body("Failed! Delete Matching Room");
    }

    @PutMapping("/closing/{activityId}")
    public ResponseEntity<?> updateIsDeadline(@PathVariable("activityId") int activityId,
                                              @RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        int isSuccess = matchingService.updateIsDeadline(userId, activityId);

        if (isSuccess == 0) {
            return ResponseEntity.status(200).body("Success! Update IsDeadLine");
        }
        return ResponseEntity.status(400).body("Failed! Update IsDeadLine");
    }

    @PutMapping("/complete/{activityId}")
    public ResponseEntity<?> updateIsComplete(@PathVariable("activityId") int activityId,
                                              @RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));

        int isSuccess = matchingService.updateIsComplete(userId, activityId);

        if (isSuccess == 0) {
            return ResponseEntity.status(200).body("Success! Update IsComplete");
        }
        return ResponseEntity.status(400).body("Failed! Update IsComplete");
    }

    @GetMapping("/participants/{activityId}")
    public ResponseEntity<?> selectParticipants(@PathVariable("activityId") int activityId,
                                               @RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        Participant participant = new Participant(userId, activityId);

        List<Participant> participantsList = matchingService.selectParticipants(participant);

        if (participantsList != null) {
            return ResponseEntity.status(200).body(participantsList);
        }
        return ResponseEntity.status(400).body("Failed! Select Participant in Matching");
    }

    @PostMapping("/participants/{activityId}")
    public ResponseEntity<?> insertParticipant(@PathVariable("activityId") int activityId,
                                               @RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        Participant participant = new Participant(userId, activityId);

        int isSuccess = matchingService.insertParticipant(participant);

        if (isSuccess == 0) {
            return ResponseEntity.status(200).body("Success! Insert Participant in Matching");
        }
        return ResponseEntity.status(400).body("Failed! Insert Participant in Matching");
    }

    @DeleteMapping("/participants/{activityId}")
    public ResponseEntity<?> deleteParticipant(@PathVariable("activityId") int activityId,
                                               @RequestHeader("Authorization") String tokenHeader) {
        String userId = jwtUtil.getIdFromToken(tokenHeader.substring(7));
        Participant participant = new Participant(userId, activityId);

        int isSuccess = matchingService.deleteParticipant(participant);

        if (isSuccess == 0) {
            return ResponseEntity.status(200).body("Success! Delete Participant in Matching");
        }
        return ResponseEntity.status(400).body("Failed! Delete Participant in Matching");
    }
}
