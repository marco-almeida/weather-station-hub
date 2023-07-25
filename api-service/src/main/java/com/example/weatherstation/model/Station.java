package com.example.weatherstation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Measurement> measurements = new ArrayList<>();
}
