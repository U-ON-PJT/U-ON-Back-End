package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;
import com.uon.matching.dto.Participant;

import java.util.List;

public interface MatchingService {
    int insertMatchingRoom(Activity activity);
    int updateMatchingRoom(Activity activity);
    List<Activity> selectAllMatchingRoom(int size, int page);
    List<Activity> selectAllMyMatchingRoom(String userId, int size, int page);
    List<Activity> selectAllMyEnterMatchingRoom(String userId, int size, int page);
    int isEnterMatchingRoom(String userId, int activityId);
    List<Activity> selectAllMatchingRoom2(int size, int page, int type, int algo, String selectDate, String parsingDongCode);
    List<Activity> selectMatchingRoomOfType(int type, int size, int page);
    Activity selectMatchingRoom(int activityId);
    int deleteMatchingRoom(String userId, int activityId);
    int updateIsDeadline(String userId, int activityId);
    int updateIsComplete(String userId, int activityId);
    List<Participant> selectParticipants(Participant participant);
    int insertParticipant(Participant participant);
    int deleteParticipant(Participant participant);
}
