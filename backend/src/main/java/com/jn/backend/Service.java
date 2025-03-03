package com.jn.backend;

import com.jn.backend.dto.RequestDto;
import com.jn.backend.dto.ResponseDto;
import com.jn.backend.enums.CommandType;
import com.jn.backend.enums.RoadDirection;
import com.jn.backend.model.AddVehicleCommand;
import com.jn.backend.model.Command;
import com.jn.backend.model.StepCommand;

@org.springframework.stereotype.Service
public class Service {
    private final int maxSteps=10;
    private int NorthVehiclesNumber;
    private int SouthVehiclesNumber;
    private int EastVehiclesNumber;
    private int WestVehiclesNumber;

    private int countAtDirectionNS(){
        return NorthVehiclesNumber + SouthVehiclesNumber;
    }

    private int countAtDirectionEW(){
        return EastVehiclesNumber + WestVehiclesNumber;
    }

    private void step(){

    }

    private void addVehicle(AddVehicleCommand command){
        switch (RoadDirection.valueOf(command.getStartRoad())) {
            case NORTH -> NorthVehiclesNumber++;
            case SOUTH -> SouthVehiclesNumber++;
            case EAST -> EastVehiclesNumber++;
            case WEST -> WestVehiclesNumber++;
            default -> throw new IllegalArgumentException("Invalid direction: " + command.getStartRoad());
        }

    }

    public ResponseDto processSimulation(RequestDto requestData){
        for (Command command : requestData.getCommands()) {
            if(command.getType().equals(CommandType.STEP)){
                step();
            }else{
                addVehicle(((AddVehicleCommand) command));
            }
        }
        return new ResponseDto();
    }
}
