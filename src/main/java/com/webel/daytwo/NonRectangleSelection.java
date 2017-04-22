package com.webel.daytwo;

import com.google.common.collect.Sets;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.IdentityHashMap;
import java.util.Set;

/**
 * 1
 * 2 3 4
 * 5 6 7 8 9
 * A B C
 * D
 * <p>
 * Created by stephenwebel1 on 12/2/16.
 */
@Slf4j
public class NonRectangleSelection {

    @Getter
    private int digit = 5;

    private static final String[] DIAMOND = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D"};
    // the remainder of the code assumes the desired keypad is rectangular
    private static int WIDTH = 5;

    private static final int UP = 'U';
    private static final int DOWN = 'D';
    private static final int LEFT = 'L';
    private static final int RIGHT = 'R';
    private static final int IN = 'I';
    private static final int OUT = 'O';

    String getSelection() {
        return DIAMOND[digit];
    }

    /**
     * This will advance the node along the numerical grid based upon a instruction in a "Cardinal direction"
     * the selection only advances if the potential location was reachable
     *
     * @param instruction Up, Down, Left, Right
     */
    void advance(char instruction) {
        //       1
        //    2  3  4
        // 5  6  7  8  9
        //    10 11 12
        //       13

        //        1
        //     2  3  4
        //  5  6  7  8  9
       //10 11 12 13 14 15 16
        //  17 18 19 20 21
        //     22 23 24
        //        25

//        1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18 19 20 21 22 23 24 25
//        X X   X X       X X                 X  X           X  X     X  X
//            1     3 3 3      5  5  5  5  5        3  3  3        1
        boolean mutate = true;
        //we can always move in
        boolean isOnLeftEdge = digit % WIDTH == 0;
        boolean isOnRightEdge = digit % WIDTH == 0;
        boolean topPyramid = digit <= 8;
        boolean bottomPyramid = digit >= 4;

        Set<Integer> illegalMoves = Sets.newHashSet();


        if (isOnLeftEdge || isOnRightEdge) {
            if (topPyramid) {
                illegalMoves.add(UP);
            }
            if (bottomPyramid) {
                illegalMoves.add(DOWN);
            }
            illegalMoves.add(isOnLeftEdge ? LEFT : RIGHT);
        }

        if (WIDTH == 1) {
            illegalMoves.add(LEFT);
            illegalMoves.add(RIGHT);
        }

        tryMove(instruction, illegalMoves);
    }

    private void tryMove(char instruction, Set<Integer> illegalMoves) {
        if (!illegalMoves.contains(Integer.valueOf(instruction))) {
            log.info("Digit: {}, Width: {}, Move: {}, IllegalMoves: {}", digit, WIDTH, Integer.valueOf(instruction), illegalMoves);
            move(instruction);
        } else {
            log.info("Digit: {}, Width: {}, Move: {}, IllegalMoves: {}", digit, WIDTH, Integer.valueOf(instruction), illegalMoves);
        }
    }

    private void move(char instruction) {
        int start = digit;

        if (instruction == RIGHT) {
            digit++;
        } else if (instruction == LEFT) {
            digit--;
        } else if (instruction == DOWN) {
            int widthDelta = digit >= 4 ? -1 : 1;
            WIDTH += widthDelta;
            digit -= WIDTH;
            WIDTH += widthDelta;
        } else if (instruction == UP) {
            int widthDelta = digit <= 8 ? -1 : 1;
            WIDTH += widthDelta;
            digit += WIDTH;
            WIDTH += widthDelta;
        }

        if (digit >= DIAMOND.length) {
            log.info("Start: {} End: {} Instruction: {} Width: {}", start, digit, instruction, WIDTH);
            System.exit(1);
        }
    }
}