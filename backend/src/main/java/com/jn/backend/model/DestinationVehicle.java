package com.jn.backend.model;

import com.jn.backend.enums.RoadDirection;

public class DestinationVehicle extends Vehicle{
    private final RoadDirection startRoad;
    private final RoadDirection endRoad;

    public DestinationVehicle(String vehicleId, RoadDirection startRoad, RoadDirection endRoad) {
        super(vehicleId);
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public RoadDirection getStartRoad() {
        return startRoad;
    }

    public RoadDirection getEndRoad() {
        return endRoad;
    }
}
