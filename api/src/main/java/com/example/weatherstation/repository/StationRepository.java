package com.example.weatherstation.repository;

import com.example.weatherstation.model.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StationRepository extends JpaRepository<Station, Long> {
    @Query("select distinct m.type from Measurement m where m.station.id = :stationId")
    List<String> findDistinctMeasurementTypesByStationId(@Param("stationId") Long stationId);
}