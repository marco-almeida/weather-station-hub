package com.example.weatherstation.controller;

import com.example.weatherstation.model.Station;
import com.example.weatherstation.service.StationService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/station")
public class StationController {

    @Autowired
    private StationService stationService;

    @GetMapping("/{station_id}")
    public ResponseEntity<Station> getStationStuff(@PathVariable("station_id") long stationId) {
        Station s = stationService.getStation(stationId);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PostMapping("/{station_id}")
    public ResponseEntity<Station> createStation(@PathVariable("station_id") long stationId) {
        Station s = stationService.createStation(stationId);
        return new ResponseEntity<>(s, HttpStatus.CREATED);
    }

}
