package com.example.weatherstation.dto;


import lombok.Data;

@Data
public class MeasurementDTO {
    private Long id;
    private Long timestamp;
    private String type;
    private double value;
    private Long stationId;
}
