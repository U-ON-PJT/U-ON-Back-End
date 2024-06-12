package com.uon.matching.model.mapper;

import com.uon.matching.dto.Activity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchingMapper {

    public int insertMatchingRoom(Activity activity);
    public int updateMatchingRoom(Activity activity);
    public List<Activity> selectAllMatchingRoom();
}
