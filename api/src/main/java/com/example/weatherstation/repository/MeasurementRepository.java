package com.example.weatherstation.repository;

import com.example.weatherstation.model.Measurement;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
    List<Measurement> findAll(Specification<Measurement> spec);
}