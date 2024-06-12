package com.uon.matching.model.service;

import com.uon.matching.dto.Activity;

public interface MatchingService {
    public int insertMatchingRoom(Activity activity);
    public int updateMatchingRoom(Activity activity);
}
