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

    public List<Measurement> getAllMeasurements() {
        return measurementRepository.findAll();
    }

    public Measurement saveMeasurementInStation(Long stationId, Measurement measurement) {
        Station s = stationRepository.findById(stationId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Station with id %d not found",
                        stationId)));
        s.addMeasurement(measurement);
        stationRepository.save(s);
        return measurement;
    }

    public List<Measurement> getAllMeasurementsWhere(String type,
                                                     List<Long> stationIds,
                                                     Float lessEqualThan,
                                                     Float greaterThan,
                                                     Long earlierEqualThan,
                                                     Long laterThan) {
        Specification<Measurement> spec = Specification.where(null);

        if (type != null) {
            spec = spec.and(MeasurementSpecifications.typeEquals(type));
        }

        if (stationIds != null) {
            spec = spec.and(MeasurementSpecifications.belongsToStations(stationIds));
        }

        if (greaterThan != null) {
            spec = spec.and(MeasurementSpecifications.valueGreaterThan(greaterThan));
        }

        if (lessEqualThan != null) {
            spec = spec.and(MeasurementSpecifications.valueLessThanOrEqual(lessEqualThan));
        }

        if (earlierEqualThan != null) {
            spec = spec.and(MeasurementSpecifications.timestampLessThanOrEqual(earlierEqualThan));
        }

        if (laterThan != null) {
            spec = spec.and(MeasurementSpecifications.timestampGreaterThan(laterThan));
        }

        return measurementRepository.findAll(spec);
    }

    public void removeMeasurement(Long measurementId) {
        Measurement m = measurementRepository.findById(measurementId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Measurement with id %d not found",
                        measurementId)));
        Station t = m.getStation();
        t.removeMeasurement(m);
        stationRepository.save(t);
    }

    public Measurement ammendMeasurement(Long measurementId, Measurement newMeasurement) {
        Measurement m = measurementRepository.findById(measurementId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Measurement with id %d not found",
                        measurementId)));
        m.setValue(newMeasurement.getValue());
        return saveMeasurementInStation(m.getStation().getId(), m);
    }
}
