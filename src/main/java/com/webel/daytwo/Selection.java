package com.webel.daytwo;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Represents a key pad selection
 * <p>
 * Created by stephenwebel1 on 12/2/16.
 */
@Slf4j
class Selection {

    @Getter
    private int digit = 5;

    // the remainder of the code assumes the desired keypad is rectangular
    private static final int LOWER_BOUND = 1;
    private static final int UPPER_BOUND = 9;
    private static final int WIDTH = 3;

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
        int start = digit;
        //Simple range check for illegal up and down commands. Ensure no-op when on the edge of keypad
        if (UP == instruction && digit - 3 >= LOWER_BOUND) {
            digit -= 3;
        } else if (DOWN == instruction && digit + 3 <= UPPER_BOUND) {
            digit += 3;
        } else if (LEFT == instruction && digit % WIDTH != 1) {
            digit--;
        } else if (RIGHT == instruction && digit % WIDTH != 0) {
            digit++;
        }

        log.info("Advanced from {} to {}", start, digit);

        if (digit > UPPER_BOUND || digit < LOWER_BOUND) {
            log.error("Out of bounds. Instruction: {} Result: {}", instruction, digit);
        }
    }


}
