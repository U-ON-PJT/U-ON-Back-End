package com.uon.matching.model.mapper;

import com.uon.matching.dto.Activity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchingMapper {

    public int insertMatchingRoom(Activity activity);
    public int updateMatchingRoom(Activity activity);
}
