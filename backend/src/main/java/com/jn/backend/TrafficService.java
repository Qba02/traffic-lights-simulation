package com.jn.backend;

import com.jn.backend.dto.RequestDto;
import com.jn.backend.dto.ResponseDto;
import com.jn.backend.enums.RoadDirection;
import com.jn.backend.model.*;

import java.sql.SQLOutput;
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
        stepsNumberMap.put(true, 5); // NS
        stepsNumberMap.put(false, 5); // WE
    }

    private int countVehiclesInDirection(boolean isNS) {
        return (isNS ? List.of(
                RoadDirection.NORTH, RoadDirection.SOUTH)
                : List.of(RoadDirection.EAST, RoadDirection.WEST))
                .stream()
                .mapToInt(dir -> vehicleQueues.get(dir).size())
                .sum();
    }

    private boolean isCrossEmpty() {
        return vehicleQueues.values().stream().allMatch(Queue::isEmpty);
    }

    private int countDirectionStepsNumber(int totalVehicles, int directionVehicles) {
        return (totalVehicles == 0) ? 0
                : (int) Math.round(((double) directionVehicles / totalVehicles) * maxSteps);
    }

    private List<String> processVehicles() {
        List<RoadDirection> activeDirections = isNS ? List.of(RoadDirection.NORTH, RoadDirection.SOUTH)
                : List.of(RoadDirection.EAST, RoadDirection.WEST);

        Queue<DestinationVehicle> queueA = vehicleQueues.get(activeDirections.get(0));
        Queue<DestinationVehicle> queueB = vehicleQueues.get(activeDirections.get(1));

        DestinationVehicle vehicleA = queueA.peek();
        DestinationVehicle vehicleB = queueB.peek();

        stepsNumberMap.put(isNS, stepsNumberMap.get(isNS) - 1);

        return moveVehiclesOffTheRoad(vehicleA, vehicleB, queueB, queueA);
    }

    private List<String> moveVehiclesOffTheRoad(DestinationVehicle vehicleA, DestinationVehicle vehicleB,
                                                Queue<DestinationVehicle> queueB, Queue<DestinationVehicle> queueA) {
        List<String> result = new ArrayList<>();

        if (vehicleA == null && vehicleB == null) {
            stepsNumberMap.put(isNS, 0);
            changeLights();
        }else if (vehicleA == null) {
            queueB.poll();
            result.add(vehicleB.getVehicleId());
        } else if (vehicleB == null) {
            queueA.poll();
            result.add(vehicleA.getVehicleId());
        }else{
            result.addAll(handleVehicleDirections(vehicleA, vehicleB, queueA, queueB));
        }
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

    private boolean isCollision(RoadDirection key, RoadDirection value) {
        return COLLISION_PAIRS.containsKey(key) && COLLISION_PAIRS.get(key).equals(value);
    }

    private void changeLights() {
        isNS = !isNS;
    }

    private void updateTrafficInfo() {
        int allVehicles = countVehiclesInDirection(true) + countVehiclesInDirection(false);
        stepsNumberMap.put(true, countDirectionStepsNumber(allVehicles, countVehiclesInDirection(true)));
        stepsNumberMap.put(false, countDirectionStepsNumber(allVehicles, countVehiclesInDirection(false)));
    }

    public List<String> nextTrafficStep() {
        if (isCrossEmpty()) {
            System.out.println("Empty cross");
            return Collections.emptyList();
        } else {
            if(stepsNumberMap.get(isNS) == 0 && stepsNumberMap.get(!isNS) == 0){
                updateTrafficInfo();
                changeLights();
            }
            if(stepsNumberMap.get(isNS) == 0){
                changeLights();
            }
            return processVehicles();
        }
    }

    public void addVehicle(DestinationVehicle vehicle) {
        vehicleQueues.get(vehicle.getStartRoad()).add(vehicle);
        System.out.println(vehicleQueues.values());
    }

    public ResponseDto processSimulation(RequestDto requestData) {
        ResponseDto resultDto = new ResponseDto();
        for (Command command : requestData.getCommands()) {
            System.out.println(command.getClass().getName());
            command.execute(this, resultDto);
        }

        return resultDto;
    }
}


