package com.jn.backend.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ResponseDto {
    private List<List<String>> stepStatuses = new ArrayList<>();;

    public ResponseDto() {}

    public void addStepResult(List<String> stepResult) {
        if (stepResult == null) {
            throw new IllegalArgumentException("Step result cannot be null");
        }
        this.stepStatuses.add(new ArrayList<>(stepResult));
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
