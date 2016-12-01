package com.webel.dayone;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * Created by swebel on 12/1/2016.
 */
@Slf4j
public class CycleAwareWalker {
    private GridPosition position = GridPosition.builder().x(0).y(0).build();
    private final Set<GridPosition> previousPositions = Sets.newHashSet(position);
    private Direction facing = Direction.NORTH;

    private GridPosition firstRepeat = null;

    public void move(MoveInstruction moveInstruction) {
        log.info("Moving: {}", moveInstruction);
        log.info("From: {}", position);
        turn(moveInstruction.getFace());
        walk(moveInstruction.getDistance());
        log.info("To: {}", position);
    }

    private void turn(String face) {
        boolean turnRight = face.equals("R");
        switch (facing) {
            case NORTH:
                facing = turnRight ? Direction.EAST : Direction.WEST;
                break;
            case SOUTH:
                facing = turnRight ? Direction.WEST : Direction.EAST;
                break;
            case EAST:
                facing = turnRight ? Direction.SOUTH : Direction.NORTH;
                break;
            case WEST:
                facing = turnRight ? Direction.NORTH : Direction.SOUTH;
                break;
        }
    }

    private void walk(int distance) {
        switch (facing) {
            case NORTH:
                walkNorth(distance);
                break;
            case SOUTH:
                walkSouth(distance);
                break;
            case EAST:
                walkEast(distance);
                break;
            case WEST:
                walkWest(distance);
                break;
        }
    }

    private void walkNorth(int distance){
        GridPosition tempGridPosition = position;
        for(int i = 1; i <= distance; i++){
            tempGridPosition = GridPosition.builder().x(position.getX()).y(position.getY()+i).build();
            checkFirstRepeat(tempGridPosition);
        }
        position = tempGridPosition;
    }

    private void walkSouth(int distance){
        GridPosition tempGridPosition = position;
        for(int i = 1; i <= distance; i++){
            tempGridPosition = GridPosition.builder().x(position.getX()).y(position.getY()-i).build();
            checkFirstRepeat(tempGridPosition);
        }
        position = tempGridPosition;
    }

    private void walkEast(int distance){
        GridPosition tempGridPosition = position;
        for(int i = 1; i <= distance; i++){
            tempGridPosition = GridPosition.builder().x(position.getX()+i).y(position.getY()).build();
            checkFirstRepeat(tempGridPosition);
        }
        position = tempGridPosition;
    }

    private void walkWest(int distance){
        GridPosition tempGridPosition = position;
        for(int i = 1; i <= distance; i++){
            tempGridPosition = GridPosition.builder().x(position.getX()-i).y(position.getY()).build();
            checkFirstRepeat(tempGridPosition);
        }
        position = tempGridPosition;
    }

    private void checkFirstRepeat(GridPosition potentialRepeat){
        if (firstRepeat == null && previousPositions.contains(potentialRepeat)){
            firstRepeat = potentialRepeat;
        }
        previousPositions.add(potentialRepeat);
    }

    public int distanceFromOriginToFirstRepeat() {
        return distanceFromOrigin(firstRepeat);
    }

    public int currentDistanceFromOrigin() {
        return distanceFromOrigin(position);
    }

    private static int distanceFromOrigin(GridPosition position) {
        return Math.abs(position.getX()) + Math.abs(position.getY());
    }
}
