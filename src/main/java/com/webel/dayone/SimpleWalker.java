package com.webel.dayone;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by swebel on 12/1/2016.
 */
@Slf4j
public class SimpleWalker {
    private int x = 0;
    private int y = 0;
    private Direction facing = Direction.NORTH;

    public void move(MoveInstruction moveInstruction) {
        log.info("Moving: {}", moveInstruction);
        turn(moveInstruction.getFace());
        walk(moveInstruction.getDistance());
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
                y += distance;
                break;
            case SOUTH:
                y -= distance;
                break;
            case EAST:
                x += distance;
                break;
            case WEST:
                x -= distance;
                break;
        }
    }

    public int distanceFromOrigin() {
        return Math.abs(x) + Math.abs(y);
    }
}
