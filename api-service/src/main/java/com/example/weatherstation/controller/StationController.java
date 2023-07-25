package com.example.weatherstation.controller;

import com.example.weatherstation.model.Station;
import com.example.weatherstation.service.StationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/station/{station_id}")
    public ResponseEntity<Station> getStationStuff(@PathVariable("station_id") long stationId) {
        Station s = stationService.getStation(stationId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @GetMapping("/stations")
    public ResponseEntity<List<Long>> getAllStationIds() {
        List<Long> s = stationService.getAllStationIds();
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/station/{station_id}")
    public ResponseEntity<Station> createStation(@PathVariable("station_id") long stationId) {
        Station s = stationService.createStation(stationId);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

}