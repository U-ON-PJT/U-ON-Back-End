package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;

import java.util.List;

public interface MatchingService {
    public int insertMatchingRoom(Activity activity);
    public int updateMatchingRoom(Activity activity);
    public List<Activity> selectAllMatchingRoom();

}
