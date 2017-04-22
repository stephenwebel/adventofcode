package com.webel.codejam;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Stephen Webel on 4/8/17.
 */
@Slf4j
public class OversizedPancakes {
    String IMPOSSIBLE = "IMPOSSIBLE";

    public static void main(String[] args) throws Exception {
        OversizedPancakes tn = new OversizedPancakes();
        tn.solve();
    }

    private void solve() throws Exception {
        //src/main/resources/A-test.in
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        log.info("Please enter a file path: ");
        String path = reader.next();
        while (!"exit".equals(path)) {
            List<String> lines = Files.readAllLines(Paths.get(path));
            BufferedWriter fw = new BufferedWriter(new FileWriter(new File(path.replace(".in", ".out"))));
            int totalLine = 0;
            int caze = 1;
            for (String line : lines) {
                if (totalLine == 0) {
                    totalLine = Integer.parseInt(line);
                } else {
                    log.info("Solving for: {}", line);
                    String[] split = line.split(" ");
                    fw.write("Case #" + caze + ": " + getNumberOfFlips(split[0], Integer.parseInt(split[1])) + "\n");
                    caze++;
                }
            }
            fw.close();
            log.info("Enter a new file path or exit: ");
            path = reader.next();
        }
    }

    private String getNumberOfFlips(String pancakes, int size) {
        log.info("Spatula: {} and cakes: {}", size, pancakes);
        int flipCount = 0;
        for (int i = 0; i + size <= pancakes.length(); i++) {
            if (pancakes.charAt(i) == '-') {
                pancakes = flip(i, size, pancakes);
                flipCount += 1;
            }
        }
        if (flipRequired(pancakes)) {
            return IMPOSSIBLE;
        }
        return String.valueOf(flipCount);


    }

    private boolean flipRequired(String pancakes) {
        for (int i = 0; i < pancakes.length(); i++) {
            if (pancakes.charAt(i) == '-')
                return true;
        }
        return false;
    }

    private String flip(int i, int size, String pancakes) {
        char[] cakes = pancakes.toCharArray();
        for (int flips = 0; flips < size; flips++) {
            cakes[i + flips] = cakes[i + flips] == '+' ? '-' : '+';
        }
        return new String(cakes);
    }
}
