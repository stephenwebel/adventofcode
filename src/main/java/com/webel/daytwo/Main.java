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
        List<String> keycodeinstructions = Files.readAllLines(Paths.get("src/main/resources/keycodeinstructions"));
        new ProblemSolver().solve(keycodeinstructions);
    }
}
