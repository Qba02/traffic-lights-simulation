package com.jn.backend.dto;

import com.jn.backend.model.Command;

import java.util.List;

public class RequestDto {
    private List<Command> commands;

    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

}



