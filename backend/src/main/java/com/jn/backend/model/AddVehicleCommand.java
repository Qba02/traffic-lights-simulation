package com.jn.backend.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jn.backend.TrafficService;
import com.jn.backend.dto.ResponseDto;
import com.jn.backend.enums.RoadDirection;

public class AddVehicleCommand extends Command{
    private DestinationVehicle vehicle;

    public AddVehicleCommand(){}

    @JsonCreator
    public AddVehicleCommand(
            @JsonProperty("vehicleId") String vehicleId,
            @JsonProperty("startRoad") String startRoad,
            @JsonProperty("endRoad") String endRoad
    ) {
        this.vehicle = new DestinationVehicle(
                vehicleId,
                RoadDirection.valueOf(startRoad.toUpperCase()),
                RoadDirection.valueOf(endRoad.toUpperCase()));
    }


    public DestinationVehicle getVehicle() {
        return vehicle;
    }
    public void setVehicle(DestinationVehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public void execute(TrafficService service, ResponseDto response) {
        service.addVehicle(vehicle);
    }
}
