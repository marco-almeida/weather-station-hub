package com.example.weatherstation.service;

import com.example.weatherstation.exception.ResourceNotFoundException;
import com.example.weatherstation.model.Station;
import com.example.weatherstation.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class StationService {
    @Autowired
    private StationRepository repository;

    public Station getStation(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Resource not Found!"));
    }

    public Station createStation(Long id) {
        return repository.save(new Station(id, new ArrayList<>()));
    }
}
