package com.jn.backend.model;

import com.jn.backend.enums.CommandType;

public class StepCommand extends Command {
    public StepCommand() {
        super(CommandType.STEP);
    }
}