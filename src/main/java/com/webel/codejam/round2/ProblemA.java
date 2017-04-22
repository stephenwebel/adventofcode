package com.webel.codejam.round2;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by Stephen Webel on 4/22/2017.
 */
@Slf4j
public class ProblemA {
    public static void main(String[] args) throws Exception {
        ProblemA solver = new ProblemA();
        solver.solve();
    }

    private void solve() throws Exception {
        //src/main/resources/A-test.in
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        log.info("scenario file: ");
        String path = reader.next();
        while (!"exit".equals(path)) {
            path = "C:\\Users\\swebel\\Desktop\\codejam\\" + path;
            List<String> lines = Files.readAllLines(Paths.get(path));
            BufferedWriter fw = new BufferedWriter(new FileWriter(new File(path.replace(".in", ".out"))));
            int totalLine = 0;
            int caze = 1;
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (totalLine == 0) {
                    totalLine = Integer.parseInt(line);
                } else {
                    String[] split = line.split(" ");
                    int totalDistance = Integer.parseInt(split[0]);
                    int numHourses = Integer.parseInt(split[1]);
                    Map<Integer, Integer> horses = Maps.newHashMap();
                    for (int h = 0; h < numHourses; h++) {
                        i++;
                        String[] horsePositionAndSpeed = lines.get(i).split(" ");
                        horses.put(Integer.valueOf(horsePositionAndSpeed[0]), Integer.valueOf(horsePositionAndSpeed[1]));
                    }
                    log.info("Solving for: {}", line);
                    fw.write(solveCase(caze, totalDistance, horses, numHourses));
                    caze++;
                }
            }
            fw.close();
            log.info("Enter a new file path or exit: ");
            path = reader.next();
        }
    }

    private String solveCase(int caze, int totalDistance, Map<Integer, Integer> horses, int numHorses) {
        double maxSpeed = Double.MAX_VALUE;
        for (Map.Entry<Integer, Integer> entry : horses.entrySet()) {
            double newMax = calcMaxSpeed(totalDistance, entry.getKey(), entry.getValue());
            if (maxSpeed > newMax) {
                maxSpeed = newMax;
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.000000");
        return "Case #" + caze + ": " + decimalFormat.format(maxSpeed) + "\n";
    }

    private double calcMaxSpeed(int totalDistance, Integer initialPosition, Integer speed) {
        Integer remainingDistance = totalDistance - initialPosition;
        double timeToComplete = remainingDistance.doubleValue() / speed.doubleValue();
        return totalDistance / timeToComplete;
    }
}
