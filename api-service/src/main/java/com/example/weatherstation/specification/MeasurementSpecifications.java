package com.example.weatherstation.specification;

import com.example.weatherstation.model.Measurement;
import org.springframework.data.jpa.domain.Specification;

public class MeasurementSpecifications {

    public static Specification<Measurement> typeEquals(String type) {
        return (root, query, builder) -> builder.equal(root.get("type"), type);
    }

    public static Specification<Measurement> valueEquals(double value) {
        return (root, query, builder) -> builder.equal(root.get("value"), value);
    }

    public static Specification<Measurement> timestampEquals(Long timestamp) {
        return (root, query, builder) -> builder.equal(root.get("timestamp"), timestamp);
    }
}
