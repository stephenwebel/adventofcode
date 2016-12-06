package com.webel.daytwo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

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

    private static final String[] STRINGS = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D"};
    // the remainder of the code assumes the desired keypad is rectangular
    private static int WIDTH = 5;
    private static int NORTHERN = 1;
    private static int SOUTHERN = -1;
    private static int PRIMARIDIAN = 0;
    private static int HEMISPHERE = PRIMARIDIAN;


    private static final int UP = 'U';
    private static final int DOWN = 'D';
    private static final int LEFT = 'L';
    private static final int RIGHT = 'R';

    /**
     * This will advance the node along the numerical grid based upon a instruction in a "Cardinal direction"
     * the selection only advances if the potential location was reachable
     *
     * @param instruction Up, Down, Left, Right
     */
    void advance(char instruction) {
//        //      1
//        //    2 3 4
//        //  5 6 7 8 9
//        //    A B C
//        //      D
//        boolean mutate = true;
//
//        if (digit == 1) {
//            onlyMoveUp(instruction);
//        }
//
//        //save this just for better logs
//        int start = digit;
//
//        if (HEMISPHERE == NORTHERN &&
//                (digit % WIDTH != 1 || digit % WIDTH == 0)) {
//
//        } else if (digit % WIDTH != 1 || digit % WIDTH == 0) {
////divide first then multiply by the xor
//        }
//
//        if (UP == instruction && digit < 9 || DOWN) {
//            digit -= --WIDTH;
//        } else if (DOWN == instruction) {
//            digit += ++WIDTH;
//        }
//
//        if (UP == instruction && digit - 3 >= LOWER_BOUND) {
//            digit -= 3;
//        } else if (DOWN == instruction && digit + 3 <= UPPER_BOUND) {
//            digit += 3;
//        } else if (LEFT == instruction && digit % WIDTH != 1) {
//            digit--;
//        } else if (RIGHT == instruction && digit % WIDTH != 0) {
//            digit++;
//        }
//
//        //0011001010
//        //1100110101
//        //1111111111
//        //1111111110
//
//        //0110100101
//        //1100110101
//        //1010010000
//
//        //1111111110
//
//        log.info("Advanced from {} to {}", start, digit);
//
//        if (digit > UPPER_BOUND || digit < LOWER_BOUND) {
//            log.error("Out of bounds. Instruction: {} Result: {}", instruction, digit);
//        }
    }

    private void onlyMoveUpNorthern(char instruction) {
        int delta = (WIDTH - 1) * (UP ^ instruction);
        digit += delta;
    }
}