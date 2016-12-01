package com.webel.dayone;

import com.google.common.collect.Lists;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by swebel on 12/1/2016.
 */
@Value
@Slf4j
public class ProblemSolver {
    String puzzleInput;


    public void solve(){
        CycleAwareWalker me = new CycleAwareWalker();

        Lists.newArrayList(puzzleInput.split(",")).stream()
                .map(String::trim)
                .map(i -> new MoveInstruction(i.substring(0, 1), Integer.parseInt(i.substring(1))))
                .forEach(me::move);

        log.info("I have traveled {} blocks", me.currentDistanceFromOrigin());
        log.info("First cycle was {} blocks", me.distanceFromOriginToFirstRepeat());
    }
}
