package com.uon.location.controller;

import com.uon.location.dto.Location;
import com.uon.location.model.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/dongCodes")
    public ResponseEntity<?> getDongCode(String sidoName, String gugunName){
//        System.out.println("sidoName: " + sidoName);
//        System.out.println("gugunName: " + gugunName);
        String dongCode= locationService.getDongCode(sidoName, gugunName);
        System.out.println(dongCode);

        return ResponseEntity.status(200).body(dongCode);
    }

    @GetMapping("/names/{dongCode}")
    public ResponseEntity<?> getLocation(@PathVariable("dongCode") String dongCode){
        Location location = locationService.getLocation(dongCode);
        System.out.println(location);

        return ResponseEntity.status(200).body(location);
    }
    @GetMapping("/codes/{parsingPlace}")
    public ResponseEntity<?> activityDongCode(@PathVariable("parsingPlace") String parsingPlace){
        String dongCode = locationService.activityDongCode(parsingPlace);
        System.out.println(parsingPlace);
        System.out.println(dongCode);

        return ResponseEntity.status(200).body(dongCode);
    }
}
