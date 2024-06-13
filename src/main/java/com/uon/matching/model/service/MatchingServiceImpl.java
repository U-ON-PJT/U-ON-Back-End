package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;
import com.uon.matching.model.mapper.MatchingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchingServiceImpl implements MatchingService {

    private final MatchingMapper matchingMapper;

    @Override
    public int insertMatchingRoom(Activity activity) {
        try {
            int isSuccess = matchingMapper.insertMatchingRoom(activity);
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateMatchingRoom(Activity activity) {
        try {
            int isSuccess = matchingMapper.updateMatchingRoom(activity);
            return isSuccess;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<Activity> selectAllMatchingRoom() {
        try {
            List<Activity> activityList = matchingMapper.selectAllMatchingRoom();
            return activityList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Activity> selectMatchingRoomOfType(int type) {
        try {
            List<Activity> activityListOfType = matchingMapper.selectMatchingRoomOfType(type);
            return activityListOfType;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Activity selectMatchingRoom(int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);
            return activityInfo;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int deleteMatchingRoom(String userId, int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);

            if (!userId.equals(activityInfo.getUserId())) {
                return -1;
            }

            matchingMapper.deleteMatchingRoom(activityId);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateIsDeadline(String userId, int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);

            if (!userId.equals(activityInfo.getUserId())) {
                return -1;
            }

            matchingMapper.updateIsDeadline(activityId);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public int updateIsComplete(String userId, int activityId) {
        try {
            Activity activityInfo = matchingMapper.selectMatchingRoom(activityId);

            if (!userId.equals(activityInfo.getUserId())) {
                return -1;
            }

            matchingMapper.updateIsComplete(activityId);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
