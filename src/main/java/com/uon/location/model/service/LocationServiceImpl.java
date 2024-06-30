package com.uon.location.model.service;

import com.uon.location.dto.Location;
import com.uon.location.model.mapper.LocationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService{

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public List<Location> getGugun(String sidoName) {
        return locationMapper.getGugun(sidoName);
    }

    @Override
    public String getDongCode(String sidoName, String gugunName) {
        return locationMapper.getDongCode(sidoName, gugunName);
    }

    @Override
    public Location getLocation(String dongCode) {
        return locationMapper.getLocation(dongCode);
    }
}
