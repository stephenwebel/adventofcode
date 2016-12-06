package com.webel.daytwo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by stephenwebel1 on 12/2/16.
 */
@Slf4j
public class Main {
    public static void main(String[] args) throws IOException {
        tesZeroOnMatchOrReturn();

//        System.out.println("Working Directory = " +
//                System.getProperty("user.dir"));
//        List<String> keycodeinstructions = Files.readAllLines(Paths.get("src/main/resources/keycodeinstructions"));
//        new ProblemSolver().solve(keycodeinstructions);
    }


    private static void tesZeroOnMatchOrReturn() {

        int UP = 'U';
        int zeroed = zeroOnMatchOrReturn('U',UP);


        log.info("Expected 0 received: {}", zeroed);


    }

    private static void matchOnXor(int a, int b) {

        int UP = 'U';
        int NOT_UP = (UP);
        int zeroed = zeroOnMatchOrReturn('U',UP);


        log.info("Expected 0 received: {}", zeroed);
    }

    private static int zeroOnMatchOrReturn(int a, int b) {
        int xor = (a ^ b);
        return a & (xor);
    }
}
