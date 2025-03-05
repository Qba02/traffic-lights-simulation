package com.jn.backend.dto;

import java.util.*;

public class ResponseDto {
    private final List<List<String>> stepStatuses = new LinkedList<>();

    public ResponseDto() {}

    public void addStepResult(List<String> stepResult) {
        if (stepResult == null) {
            throw new IllegalArgumentException("Step result cannot be null");
        }
        this.stepStatuses.add(stepResult);
    }
    public void clearStepStatuses() {
        this.stepStatuses.clear();
    }

    public List<List<String>> getStepStatuses() {
        List<List<String>> deepCopy = new ArrayList<>();
        for (List<String> stepResult : stepStatuses) {
            deepCopy.add(Collections.unmodifiableList(stepResult));
        }
        return Collections.unmodifiableList(deepCopy);
    }
}
