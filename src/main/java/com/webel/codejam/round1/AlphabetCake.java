package com.webel.codejam.round1;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Stephen Webel on 4/14/2017.
 */
@Slf4j
public class AlphabetCake {
    public static void main(String[] args) throws Exception {
        new AlphabetCake().solve();
    }

    private void solve() throws Exception {
        log.info("solving");
        List<String> strings = Files.readAllLines(Paths.get("src/main/resources/A-small-attempt0.in"));
        strings.remove(0);
        BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/A-small.out"));
        int currentR = 0;
        int currentC = 0;
        List<String> inputs = Lists.newArrayList();
        int caze = 0;
        for (String line : strings) {
            String[] split = line.split(" ");
            if (split.length == 2) {
                if (!inputs.isEmpty()) {
                    writer.write(solve(++caze, currentR, currentC, inputs));
                }
                currentR = Integer.parseInt(split[0]);
                currentC = Integer.parseInt(split[1]);
            } else {
                inputs.add(line);
            }
        }
        writer.write(solve(++caze, currentR, currentC, inputs));
        writer.close();
    }

    private String solve(int caze, int R, int C, List<String> lines) {
        log.info("solve for ({},{}):\n {}", R, C, lines);
        List<Pair<Integer, Integer>> originalValues = Lists.newArrayList();
        char[][] grid = new char[R][C];
        int rowBuild = 0;
        for (String line : lines) {
            int colBuild = 0;
            for (char c : line.toCharArray()) {
                grid[rowBuild][colBuild] = c;
                if (c != '?') {
                    originalValues.add(Pair.of(rowBuild, colBuild));
                }
                colBuild++;
            }
            rowBuild++;
        }
        for (Pair<Integer, Integer> originalValue : originalValues) {
            build(originalValue.getLeft(), originalValue.getRight(), grid);
        }

        String result = "";
        for (int i = 0; i < R; i++) {
            result += new String(grid[i]) + "\n";
        }
        return "Case #" + caze + ":\n" + result;
    }

    private void build(int row, int col, char[][] startgrid) {
        char val = startgrid[row][col];

        if (check(row, col + 1, startgrid, val) || check(row, col - 1, startgrid, val)) {
            fillRow(row, col, startgrid, val);
        } else if (check(row - 1, col, startgrid, val) || check(row + 1, col, startgrid, val)) {
            fillCol(row, col, startgrid, val);
        } else if (check(row, col + 1, startgrid, '?') || check(row, col - 1, startgrid, '?')) {
            fillRow(row, col, startgrid, val);
        } else {
            fillCol(row, col, startgrid, val);
        }
    }

    private void fillRow(int row, int col, char[][] startgrid, char val) {
        int c = col;
        while (c >= 0 && (startgrid[row][c] == val || startgrid[row][c] == '?')) {
            startgrid[row][c] = val;
            c--;
        }
        c = col;
        while (c < startgrid[row].length && (startgrid[row][c] == val || startgrid[row][c] == '?')) {
            startgrid[row][c] = val;
            c++;
        }
    }

    private void fillCol(int row, int col, char[][] startgrid, char val) {
        int r = row;
        while (r >= 0 && (startgrid[r][col] == val || startgrid[r][col] == '?')) {
            startgrid[r][col] = val;
            r--;
        }
        r = row;
        while (r < startgrid.length && (startgrid[r][col] == val || startgrid[r][col] == '?')) {
            startgrid[r][col] = val;
            r++;
        }
    }

    private boolean check(int row, int col, char[][] startgrid, char val) {
        if (row >= startgrid.length || row < 0) {
            return false;
        } else if (col >= startgrid[row].length || col < 0) {
            return false;
        } else {
            return startgrid[row][col] == val;
        }
    }
}
