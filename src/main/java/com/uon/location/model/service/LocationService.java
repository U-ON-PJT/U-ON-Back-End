package com.uon.location.model.service;

import com.uon.location.dto.Location;

import java.util.List;

public interface LocationService {
    List<Location> getGugun(String sidoName);

    String getDongCode(String sidoName, String gugunName);

    Location getLocation(String dongCode);

    String activityDongCode(String parsingPlace);
}
