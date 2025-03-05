package com.jn.backend;

import com.jn.backend.dto.RequestDto;
import com.jn.backend.dto.ResponseDto;
import com.jn.backend.enums.RoadDirection;
import com.jn.backend.model.*;

import java.util.*;

@org.springframework.stereotype.Service
public class TrafficService {
    private final int maxSteps = 10;
    private boolean isNS = true;

    private final Map<RoadDirection, RoadDirection> COLLISION_PAIRS = Map.of(
            RoadDirection.NORTH, RoadDirection.EAST,
            RoadDirection.SOUTH, RoadDirection.WEST,
            RoadDirection.EAST, RoadDirection.SOUTH,
            RoadDirection.WEST, RoadDirection.NORTH
    );
    private final Map<RoadDirection, Queue<DestinationVehicle>> vehicleQueues = new EnumMap<>(RoadDirection.class);
    private final Map<Boolean, Integer> stepsNumberMap = new HashMap<>();

    public TrafficService() {
        for (RoadDirection direction : RoadDirection.values()) {
            vehicleQueues.put(direction, new LinkedList<>());
        }
        stepsNumberMap.put(true, 0); // NS
        stepsNumberMap.put(false, 0); // WE
    }

    public ResponseDto processSimulation(RequestDto requestData) {
        ResponseDto resultDto = new ResponseDto();
        resetLightsState();
        for (Command command : requestData.getCommands()) {
            command.execute(this, resultDto);
        }

        return resultDto;
    }

    @SuppressWarnings("ReassignedVariable")
    public List<String> nextTrafficStep() {
        if (isCrossEmpty()) {
            return Collections.emptyList();
        } else {
            if(stepsNumberMap.get(isNS) == 0 && stepsNumberMap.get(!isNS) == 0){
                updateLightsState();
                changeLights();
            }
            if(stepsNumberMap.get(isNS) == 0){
                changeLights();
            }

            List<RoadDirection> activeDirections = getRoadDirectionsList(isNS);
            if (areQueuesEmpty(activeDirections)) {
                stepsNumberMap.put(isNS, 0);
                changeLights();
                activeDirections = getRoadDirectionsList(isNS);
            }

            return moveVehiclesOffTheRoad(
                    vehicleQueues.get(activeDirections.get(0)),
                    vehicleQueues.get(activeDirections.get(1))
            );
        }
    }

    public void addVehicle(DestinationVehicle vehicle) {
        vehicleQueues.get(vehicle.getStartRoad()).add(vehicle);
    }

    private List<String> moveVehiclesOffTheRoad(Queue<DestinationVehicle> queueB, Queue<DestinationVehicle> queueA) {
        List<String> result = new ArrayList<>();
        DestinationVehicle vehicleA = queueA.peek();
        DestinationVehicle vehicleB = queueB.peek();

        if (vehicleA == null) {
            queueB.poll();
            result.add(vehicleB.getVehicleId());
        } else if (vehicleB == null) {
            queueA.poll();
            result.add(vehicleA.getVehicleId());
        }else{
            result.addAll(handleVehicleDirections(vehicleA, vehicleB, queueA, queueB));
        }
        stepsNumberMap.put(isNS, stepsNumberMap.get(isNS) - 1);
        return result;
    }

    private List<String> handleVehicleDirections(DestinationVehicle vehicleA, DestinationVehicle vehicleB,
                                                 Queue<DestinationVehicle> queueA, Queue<DestinationVehicle> queueB) {
        List<String> result = new ArrayList<>();
        if (isCollision(vehicleA.getEndRoad(), vehicleB.getEndRoad())){
            queueA.poll();
            result.add(vehicleA.getVehicleId());
        }else if(isCollision(vehicleB.getEndRoad(), vehicleA.getEndRoad())){
            queueB.poll();
            result.add(vehicleB.getVehicleId());
        } else {
            queueA.poll();
            queueB.poll();
            Collections.addAll(result, vehicleA.getVehicleId(), vehicleB.getVehicleId());
        }
        return result;
    }

    private void updateLightsState() {
        int allVehicles = countVehiclesInDirection(true) + countVehiclesInDirection(false);
        stepsNumberMap.put(true, countDirectionStepsNumber(allVehicles, countVehiclesInDirection(true)));
        stepsNumberMap.put(false, countDirectionStepsNumber(allVehicles, countVehiclesInDirection(false)));
    }

    private void resetLightsState(){
        stepsNumberMap.put(true, 0);
        stepsNumberMap.put(false, 0);
        for (RoadDirection direction : RoadDirection.values()) {
            vehicleQueues.replace(direction, new LinkedList<>());
        }
    }

    private int countVehiclesInDirection(boolean isNS) {
        return getRoadDirectionsList(isNS)
                .stream()
                .mapToInt(dir -> vehicleQueues.get(dir).size())
                .sum();
    }

    private static List<RoadDirection> getRoadDirectionsList(boolean isNS) {
        return isNS ? List.of(
                RoadDirection.NORTH, RoadDirection.SOUTH)
                : List.of(RoadDirection.EAST, RoadDirection.WEST);
    }

    private boolean isCrossEmpty() {
        return vehicleQueues.values().stream().allMatch(Queue::isEmpty);
    }

    private boolean areQueuesEmpty(List<RoadDirection> directions) {
        return vehicleQueues.get(directions.get(0)).isEmpty()
                && vehicleQueues.get(directions.get(1)).isEmpty();
    }

    private int countDirectionStepsNumber(int totalVehicles, int directionVehicles) {
        return (totalVehicles == 0) ? 0
                : (int) Math.round(((double) directionVehicles / totalVehicles) * maxSteps);
    }

    private boolean isCollision(RoadDirection key, RoadDirection value) {
        return COLLISION_PAIRS.containsKey(key) && COLLISION_PAIRS.get(key).equals(value);
    }

    private void changeLights() {
        isNS = !isNS;
    }

}


