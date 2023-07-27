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
@CrossOrigin
@RequestMapping("api/v1")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/station/{id}")
    public Station getStationStuff(@PathVariable("id") long stationId) {
        return stationService.getStation(stationId);
    }

    @GetMapping("/stations")
    public List<Long> getAllStationIds() {
        return stationService.getAllStationIds();
    }

    @PostMapping("/station/{id}")
    public ResponseEntity<Station> createStation(@PathVariable("id") long stationId) {
        Station s = stationService.createStation(stationId);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

}
