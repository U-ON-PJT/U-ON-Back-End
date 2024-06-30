package com.uon.matching.model.mapper;

import com.uon.matching.dto.Activity;
import com.uon.matching.dto.Participant;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface MatchingMapper {

    int insertMatchingRoom(Activity activity);
    int updateMatchingRoom(Activity activity);
    List<Activity> selectAllMatchingRoom(Map<String, Object> paramMap);

    List<Activity> selectAllMatchingRoom2(Map<String, Object> paramMap);
    List<Activity> selectMatchingRoomOfType(Map<String, Object> paramMap);
    Activity selectMatchingRoom(int activityId);
    int deleteMatchingRoom(int activityId);
    int updateIsDeadline(int activityId);
    int updateIsComplete(int activityId);
    int isContainsMatchingParticipant(Participant participant);
    List<Participant> selectParticipants(int activityId);
    int insertParticipant(Participant participant);
    int deleteParticipant(Participant participant);
}
