package com.uon.matching.model.mapper;

import com.uon.matching.dto.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchingMapper {

    int insertMatchingRoom(Activity activity);
    int updateMatchingRoom(Activity activity);
    List<Activity> selectAllMatchingRoom();
    List<Activity> selectMatchingRoomOfType(int type);
    Activity selectMatchingRoom(int activityId);
    int deleteMatchingRoom(int activityId);
    int updateIsDeadline(int activityId);
    int updateIsComplete(int activityId);
}
