package com.webel.codejam.round1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Stephen Webel on 4/14/2017.
 */
public class PlayTheDragon {
    public static void main(String[] args) throws Exception {
        new PlayTheDragon().solve();
    }

    private void solve() throws Exception {
        List<String> strings = Files.readAllLines(Paths.get("src/main/resources/A-small.in"));
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/A-small.in"));
        boolean firstLine = true;
        for (String string : strings) {
            if (firstLine) {
                firstLine = false;
            } else {
                writer.write(solveLine(string));
            }
        }
        writer.close();
    }

    private String solveLine(String line) {
        return null;
    }
}
