package com.example.weatherstation.service;

import com.example.weatherstation.exception.ResourceNotFoundException;
import com.example.weatherstation.model.Measurement;
import com.example.weatherstation.repository.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {
    @Autowired
    private MeasurementRepository repository;

    public Measurement getMeasurement(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));
    }

}
