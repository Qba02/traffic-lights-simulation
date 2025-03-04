package com.jn.backend.enums;

public enum RoadDirection {
    NORTH, WEST, SOUTH, EAST;
    public static RoadDirection fromString(String value) {
        try {
            return RoadDirection.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid road direction: " + value);
        }
    }
}
