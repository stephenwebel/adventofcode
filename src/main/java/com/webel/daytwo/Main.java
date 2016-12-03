package com.webel.daytwo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by stephenwebel1 on 12/2/16.
 */
public class Main {

    public static final ProblemSolver PROBLEM_SOLVER = new ProblemSolver();


    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        List<String> keycodeinstructions = Files.readAllLines(Paths.get("src/main/resources/keycodeinstructions"));
        PROBLEM_SOLVER.solve(keycodeinstructions);
    }

}
