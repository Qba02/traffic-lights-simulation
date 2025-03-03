package com.jn.backend.model;

import com.jn.backend.enums.CommandType;

public class AddVehicleCommand extends Command{
    private final String vehicleId;
    private final String startRoad;
    private final String endRoad;


    public AddVehicleCommand(String vehicleId, String startRoad, String endRoad) {
        super(CommandType.ADD_VEHICLE);
        this.vehicleId = vehicleId;
        this.startRoad = startRoad;
        this.endRoad = endRoad;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public String getStartRoad() {
        return startRoad;
    }

    public String getEndRoad() {
        return endRoad;
    }
}
