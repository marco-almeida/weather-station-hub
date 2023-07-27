package com.example.weatherstation.service;

import com.example.weatherstation.exception.ResourceAlreadyExistsException;
import com.example.weatherstation.exception.ResourceNotFoundException;
import com.example.weatherstation.model.Station;
import com.example.weatherstation.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StationService {
    @Autowired
    private StationRepository repository;

    public List<String> availableTypesByStation(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Station with id %d not found", id));
        }
        return repository.findDistinctMeasurementTypesByStationId(id);
    }

    public Station getStation(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Station with id %d not found", id)));
    }

    public List<Long> getAllStationIds() {
        return repository.findAll().stream().map(Station::getId).toList();
    }

    public Station createStation(Long id) {
        if (repository.existsById(id)) {
            throw new ResourceAlreadyExistsException(String.format("Station with id %d already exists!", id));
        }
        return repository.save(new Station(id, new ArrayList<>()));
    }
}
