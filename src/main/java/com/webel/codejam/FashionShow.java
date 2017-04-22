package com.webel.codejam;

import com.google.common.collect.Lists;
import com.sun.tools.internal.ws.processor.model.Model;
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
public class FashionShow {
    public static void main(String[] args) throws Exception {
        FashionShow tn = new FashionShow();
        tn.solve();
    }

    private void solve() throws Exception {
        //src/main/resources/A-test.in
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        log.info("Please enter a file path: ");
        String path = reader.next();
        while (!"exit".equals(path)) {
            path = "src/main/resources/" + path;
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
                    String[] split = line.split(" ");
                    int N = Integer.parseInt(split[0]);
                    int M = Integer.parseInt(split[1]);
                    List<ModelPosition> positions = Lists.newArrayList();
                    for (int m = 1; m <= M; m++) {
                        positions.add(parseModelPosition(lines.get(i + m)));
                    }
                    i = i + M;
                    fw.write(caseOut(caze, N, positions));
                    caze++;
                }
            }
            fw.close();
            log.info("Enter a new file path or exit: ");
            path = reader.next();
        }
    }

    private String caseOut(int caze, int n, List<ModelPosition> positions) {
        log.info("creating {} X {} grid with initial models {}", n, n, positions);
        String[][] stage = initStage(n, positions);
        log.info("Initial Stage:\n{}", prettyPrintStage(stage));
        List<ModelPosition> originalPositions = Lists.newArrayList(positions);
        List<ModelPosition> changedModels = addModels(stage);
        String output = "Case #" + caze + ": " +
                calcStyle(originalPositions, changedModels) + " " + changedModels.size() + "\n";
        for (ModelPosition modelPosition : changedModels) {
            output = output + modelPosition + "\n";
        }
        return output;
    }

    private List<ModelPosition> addModels(String[][] stage) {
        List<ModelPosition> newModels = Lists.newArrayList();
        for (int row = 0; row < stage.length; row++) {
            for (int col = 0; col < stage.length; col++) {
                boolean cRow = cleanRow(stage, row);
                boolean cCol = cleanCol(stage, col);
                boolean cDiag = cleanDiag(stage, row, col);
                log.info("State row: {}, col: {}, diag: {}\n({},{})\n{}", cRow, cCol, cDiag, row, col, prettyPrintStage(stage));
                if (".".equals(stage[row][col])) {
                    if (cRow && cCol && cDiag) {
                        newModels.add(new ModelPosition("o", row, col));
                        stage[row][col] = "o";
                    } else if (cDiag) {
                        newModels.add(new ModelPosition("+", row, col));
                        stage[row][col] = "+";
                    } else {
                        newModels.add(new ModelPosition("x", row, col));
                        stage[row][col] = "x";
                    }
                } else {
                    ModelPosition newPosition = trySwap(stage, row, col);
                    if (newPosition != null) {
                        newModels.add(newPosition);
                    }
                }

            }
        }
        return newModels;
    }

    private boolean cleanCol(String[][] stage, int col) {
        int n = stage.length;
        boolean clean = true;
        for (int i = 0; i < n; i++) {
            String val = stage[i][col];
            if ("+".equals(val)) {
                return true;
            } else if ("o".equals(val) || "x".equals(val)) {
                clean = false;
            }
        }
        return clean;
    }

    private boolean cleanRow(String[][] stage, int row) {
        String[] rowVals = stage[row];
        boolean clean = true;
        for (String val : rowVals) {
            if ("+".equals(val)) {
                return true;
            } else if ("o".equals(val) || "x".equals(val)) {
                clean = false;
            }
        }
        return clean;
    }

    private boolean cleanDiag(String[][] stage, final int row, final int col) {
        log.info("Clean Diag check for ({},{})\n{}", row, col, prettyPrintStage(stage));
        log.info("diag1 {}", checkDiag1(stage, row, col) );
        log.info("diag2 {}", checkDiag2(stage, row, col) );
        return checkDiag1(stage, row, col) && checkDiag2(stage, row, col);
    }

    private boolean checkDiag2(String[][] stage, int row, int col) {
        int n = stage.length;
        int bound = n - 1;
        int r1 = Math.min(bound, row + col);
        int c1 = Math.max(0, col - (bound - row));
        boolean clean = true;
        while (r1 > 0 && c1 < n) {
//            log.info("check ({},{}) == x | o | +", r1, c1);
            String val = stage[r1][c1];
            if ("x".equals(val)) {
                return true;
            } else if ("+".equals(val) || "o".equals(val)) {
                clean = false;
            }
            r1--;
            c1++;
        }
        return clean;
    }

    private boolean checkDiag1(String[][] stage, int row, int col) {
        int n = stage.length;
        int r1 = Math.max(0, row - col);
        int c1 = Math.max(0, col - row);
        boolean clean = true;
        while (r1 < n && c1 < n) {
            String val = stage[r1][c1];
            if ("x".equals(val)) {
                return true;
            } else if ("+".equals(val) || "o".equals(val)) {
                clean = false;
            }
            r1++;
            c1++;
        }
        return clean;
    }


    private ModelPosition trySwap(String[][] stage, int i, int j) {
//        String startStyle = stage[i][j];
//        if ("o".equals(startStyle)) {
//            return null;
//        }
//        stage[i][j] = ".";
//        if (cleanSpot(stage, i, j)) {
//            stage[i][j] = "o";
//            return new ModelPosition("o", i, j);
//        } else {
//            stage[i][j] = startStyle;
//        }
        return null;
    }

    private ModelPosition parseModelPosition(String s) {
        String[] split = s.split(" ");
        return new ModelPosition(split[0], Integer.parseInt(split[1]) - 1, Integer.parseInt(split[2]) - 1);
    }

    private String[][] initStage(int n, List<ModelPosition> positions) {
        String[][] stage = new String[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stage[i][j] = ".";
            }
        }
        for (ModelPosition modelPosition : positions) {
            stage[modelPosition.row][modelPosition.col] = modelPosition.modelType;
        }
        return stage;
    }

    private String prettyPrintStage(String[][] stage) {
        int n = stage.length;
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                str.append(stage[i][j]).append(" ");
            }
            str.append("\n");
        }
        return str.toString();
    }

    private int calcStyle(List<ModelPosition> positions, List<ModelPosition> addedModels) {
        int points = 0;
        for (ModelPosition modelPosition : positions) {
            if (modelPosition.modelType.equals("+") | modelPosition.modelType.equals("x")) {
                points++;
            } else if (modelPosition.modelType.equals("o")) {
                points += 2;
            }
        }
        for (ModelPosition modelPosition : addedModels) {
            if (modelPosition.modelType.equals("+") | modelPosition.modelType.equals("x")) {
                points++;
            } else if (modelPosition.modelType.equals("o")) {
                points += 2;
            }
        }
        return points;
    }

    public class ModelPosition {
        public final String modelType;
        public final int row;
        public final int col;

        public ModelPosition(String modelType, int row, int col) {
            this.modelType = modelType;
            this.row = row;
            this.col = col;
        }

        @Override
        public String toString() {
            return modelType + " " + (row + 1) + " " + (col + 1);
        }
    }
}
