package com.example.weatherstation.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "station")
public class Station {
    @Id
    private Long id;

    @OneToMany(cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "station")
    private List<Measurement> measurements = new ArrayList<>();

    public void addMeasurement(Measurement measurement) {
        measurements.add(measurement);
        measurement.setStation(this);
    }

    public void removeMeasurement(Measurement measurement) {
        measurements.remove(measurement);
        measurement.setStation(null);
    }
}
