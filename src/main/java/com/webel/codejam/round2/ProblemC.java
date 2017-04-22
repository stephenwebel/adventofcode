package com.webel.codejam.round2;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Stephen Webel on 4/22/2017.
 */
@Slf4j
public class ProblemC {
    public static void main(String[] args) throws Exception {
        ProblemC solver = new ProblemC();
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
                    log.info("Solving for: {}", line);
                    fw.write(solveCase(caze));
                    caze++;
                }
            }
            fw.close();
            log.info("Enter a new file path or exit: ");
            path = reader.next();
        }
    }

    private String solveCase(int caze) {
        return "Case #"+caze+":";
    }
}
