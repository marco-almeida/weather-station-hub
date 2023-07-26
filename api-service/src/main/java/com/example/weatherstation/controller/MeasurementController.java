package com.example.weatherstation.controller;

import com.example.weatherstation.model.Measurement;
import com.example.weatherstation.service.MeasurementService;
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
public class MeasurementController {

    @Autowired
    private MeasurementService measurementService;

    @PostMapping("/station/{station_id}/payload")
    public ResponseEntity<Measurement> submitMeasurement(@PathVariable("station_id") long stationId,
                                                         @RequestBody Measurement measurement) {
        Measurement m = measurementService.saveMeasurementInStation(stationId, measurement);
        return new ResponseEntity<>(m, HttpStatus.OK);
    }

    @GetMapping("stations/payloads")
    public ResponseEntity<List<Measurement>> getAllMeasurements() {
        List<Measurement> measurements = measurementService.getAllMeasurements();
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

    @GetMapping("/measurement")
    public ResponseEntity<List<Measurement>> getMeasurementsWhere(@RequestParam String type,
                                                                  @RequestParam(name = "station_ids", required = false) List<Long> stationIds) {
        List<Measurement> measurements = measurementService.getAllMeasurementsWhere(type);
        if (stationIds != null) {
            measurements = measurements.stream().filter(x -> stationIds.contains(x.getStation().getId())).toList();
        }
        return new ResponseEntity<>(measurements, HttpStatus.OK);
    }

}
