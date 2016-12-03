package com.webel.daytwo;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by stephenwebel1 on 12/2/16.
 */
@Slf4j
public class ProblemSolver {

    public void solve(List<String> keyCodeInstructions) {
        final Selection selection = new Selection();

        StringBuilder stringBuilder = new StringBuilder(keyCodeInstructions.size());

        keyCodeInstructions.forEach(instruction -> {
            log.info("NEW INSTRUCTION: {}", instruction);
            if (!instruction.trim().equals("")) {
                for (char a : instruction.toCharArray()) {
                    selection.advance(a);
                }
                log.info("Press {}", selection.getDigit());
                stringBuilder.append(selection.getDigit());
            }
        });
        log.info("{}", stringBuilder.toString());

    }

}
