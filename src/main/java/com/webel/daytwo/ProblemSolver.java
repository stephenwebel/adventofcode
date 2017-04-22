package com.webel.daytwo;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Created by stephenwebel1 on 12/2/16.
 */
@Slf4j
class ProblemSolver {
    void solve(List<String> keyCodeInstructions) {
//        final Selection selection = new Selection();
        final NonRectangleSelection selection = new NonRectangleSelection();

        StringBuilder stringBuilder = new StringBuilder(keyCodeInstructions.size());

        keyCodeInstructions.forEach(instruction -> {
            log.info("NEW INSTRUCTION: {}", instruction);
            if (!instruction.trim().equals("")) {
                for (char a : instruction.toCharArray()) {
                    selection.advance(a);
                }
                log.info("Press {}", selection.getSelection());
                stringBuilder.append(selection.getSelection());
            }
        });
        log.info("{}", stringBuilder.toString());
    }
}
