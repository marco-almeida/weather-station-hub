package com.example.weatherstation.repository;

import com.example.weatherstation.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StationRepository extends JpaRepository<Station, Long> {
}