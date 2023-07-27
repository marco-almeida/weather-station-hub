package com.example.weatherstation.controller;

import com.example.weatherstation.dto.MeasurementDTO;
import com.example.weatherstation.model.Measurement;
import com.example.weatherstation.service.MeasurementService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private MeasurementService measurementService;

    @PostMapping("/station/{station_id}/payload")
    public ResponseEntity<MeasurementDTO> submitMeasurement(@PathVariable("station_id") long stationId,
                                                            @RequestBody Measurement measurement) {
        Measurement m = measurementService.saveMeasurementInStation(stationId, measurement);
        return new ResponseEntity<>(modelMapper.map(m, MeasurementDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("stations/payloads")
    public List<MeasurementDTO> getAllMeasurements() {
        List<Measurement> measurements = measurementService.getAllMeasurements();
        return measurements.stream().map(m -> modelMapper.map(m, MeasurementDTO.class)).toList();
    }

    @GetMapping("/measurement")
    public List<MeasurementDTO> getMeasurementsWhere(@RequestParam(required = false) String type,
                                                     @RequestParam(name = "station_ids", required = false) List<Long> stationIds,
                                                     @RequestParam(name = "greater_than", required = false) Float greatherThan,
                                                     @RequestParam(name = "less_equal_than", required = false) Float lessEqualThan,
                                                     @RequestParam(name = "earlier_equal_than", required = false) Long earlierEqualThan,
                                                     @RequestParam(name = "later_than", required = false) Long laterThan) {
        List<Measurement> measurements = measurementService.getAllMeasurementsWhere(type,
                stationIds,
                lessEqualThan,
                greatherThan,
                earlierEqualThan,
                laterThan);
        return measurements.stream().map(m -> modelMapper.map(m, MeasurementDTO.class)).toList();
    }

    @DeleteMapping("measurement/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMeasurement(@PathVariable("id") Long measurementId) {
        measurementService.removeMeasurement(measurementId);
    }

    @PatchMapping("/measurement/{id}")
    public Measurement ammendMeasurement(@PathVariable(value = "id") Long measurementId,
                                         @RequestBody Measurement measurementDetails) {
        return measurementService.ammendMeasurement(measurementId, measurementDetails);
    }
}
