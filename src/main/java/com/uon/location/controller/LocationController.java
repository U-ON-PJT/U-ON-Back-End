package com.uon.location.controller;

import com.uon.location.dto.Location;
import com.uon.location.model.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/locations")
@Slf4j
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/guguns")
    public ResponseEntity<?> getGugun(String sidoName){
        List<Location> list = locationService.getGugun(sidoName);
        System.out.println(list);

        return ResponseEntity.status(200).body(list);
    }
}
