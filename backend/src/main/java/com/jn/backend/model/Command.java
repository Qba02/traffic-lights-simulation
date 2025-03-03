package com.jn.backend.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jn.backend.enums.CommandType;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AddVehicleCommand.class, name = "addVehicle"),
        @JsonSubTypes.Type(value = StepCommand.class, name = "step")
})
public abstract class Command {
    private CommandType type;
    public Command(){}

    public Command(CommandType t){
        this.type = t;
    }
    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }
}
