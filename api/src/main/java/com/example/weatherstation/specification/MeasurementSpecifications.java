package com.example.weatherstation.specification;

import com.example.weatherstation.model.Measurement;
import org.springframework.data.jpa.domain.Specification;

public class MeasurementSpecifications {

    public static Specification<Measurement> typeEquals(String type) {
        return (root, query, builder) -> builder.equal(root.get("type"), type);
    }

    public static Specification<Measurement> valueGreaterThan(double value) {
        return (root, query, builder) -> builder.greaterThan(root.get("value"), value);
    }

    public static Specification<Measurement> valueLessThanOrEqual(double value) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("value"), value);
    }

    public static Specification<Measurement> timestampGreaterThan(long timestamp) {
        return (root, query, builder) -> builder.greaterThan(root.get("timestamp"), timestamp);
    }

    public static Specification<Measurement> timestampLessThanOrEqual(long timestamp) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("timestamp"), timestamp);
    }

}
