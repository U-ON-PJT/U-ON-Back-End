package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;

import java.util.List;

public interface MatchingService {
    int insertMatchingRoom(Activity activity);
    int updateMatchingRoom(Activity activity);
    List<Activity> selectAllMatchingRoom();
    List<Activity> selectMatchingRoomOfType(int type);
    Activity selectMatchingRoom(int activityId);
    int deleteMatchingRoom(String userId, int activityId);
}
