package com.example.weatherstation.service;

import com.example.weatherstation.exception.ResourceNotFoundException;
import com.example.weatherstation.model.Measurement;
import com.example.weatherstation.model.Station;
import com.example.weatherstation.repository.MeasurementRepository;
import com.example.weatherstation.repository.StationRepository;
import com.example.weatherstation.specification.MeasurementSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeasurementService {
    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private StationRepository stationRepository;

    public Measurement getMeasurement(Long id) {
        return measurementRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));
    }

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public Measurement saveMeasurementInStation(Long stationId, Measurement measurement) {
        Station s = stationRepository.findById(stationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Station with id %d does not exist",
                        stationId)));
        s.addMeasurement(measurement);
        stationRepository.save(s);
        return measurement;
    }

    public List<Measurement> getAllMeasurementsWhere(String type, List<Long> stationIds, Float biggerThan, Long laterThan) {
        Specification<Measurement> spec = Specification.where(null);

        if (type != null) {
            spec = spec.and(MeasurementSpecifications.typeEquals(type));
        }

//        if (value != null) {
//            spec = spec.and(MeasurementSpecifications.valueEquals(value));
//        }
//
//        if (timestamp != null) {
//            spec = spec.and(MeasurementSpecifications.timestampEquals(timestamp));
//        }

        return measurementRepository.findAll(spec);
    }


}
