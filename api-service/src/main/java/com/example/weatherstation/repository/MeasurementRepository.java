package com.example.weatherstation.repository;

import com.example.weatherstation.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
}