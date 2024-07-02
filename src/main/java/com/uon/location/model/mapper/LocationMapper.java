package com.uon.location.model.mapper;

import com.uon.location.dto.Location;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LocationMapper {

    List<Location> getGugun(String sidoName);

    String getDongCode(String sidoName, String gugunName);

    Location getLocation(String dongCode);

    String activityDongCode(String parsingPlace);
}
