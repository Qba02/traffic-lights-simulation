package com.jn.backend.model;

import com.jn.backend.TrafficService;
import com.jn.backend.dto.ResponseDto;

public class StepCommand extends Command {
    public StepCommand(){}
    @Override
    public void execute(TrafficService service, ResponseDto response) {
        response.addStepResult(service.nextTrafficStep());
    }
}