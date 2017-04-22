package com.webel.codejam.round2;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by Stephen Webel on 4/22/2017.
 */
@Slf4j
public class ProblemB {
    char[] colors = new char[]{'R', 'O', 'Y', 'G', 'B', 'V'};
    ImmutableMap<Object, Object> exclusions = ImmutableMap.builder()
            .put('R', Sets.newHashSet('R', 'O', 'V'))
            .put('O', Sets.newHashSet('O', 'R', 'Y'))
            .put('Y', Sets.newHashSet('Y', 'G', 'O'))
            .put('G', Sets.newHashSet('G', 'Y', 'B'))
            .put('B', Sets.newHashSet('B', 'G', 'V'))
            .put('V', Sets.newHashSet('V', 'B', 'R')).build();

    public static void main(String[] args) throws Exception {
        ProblemB solver = new ProblemB();
        solver.solve();
    }

    private void solve() throws Exception {
        String path = "B-large-practice.in";
        path = "C:\\Users\\swebel\\Desktop\\codejam\\" + path;
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
                String solution = solveCase(caze, split);
                log.info("Solved: {}", solution);
                fw.write(solution);
                caze++;
            }
        }
        fw.close();
    }

    private String solveCase(int caze, String[] split) {
        List<Unicorn> unicorns = Lists.newArrayList();
        int totalUnicorns = Integer.parseInt(split[0]);
        for (int i = 1; i < split.length; i++) {
            int requires = Integer.parseInt(split[i]);
            if (requires != 0) {
                unicorns.add(new Unicorn(colors[i - 1], requires, 0));
            }
        }
        String unicornArrangement = solveForUnicorns(unicorns);
        if (!"IMPOSSIBLE".equals(unicornArrangement)) {
            if (unicornArrangement.length() != totalUnicorns) {
                log.error("needed {} found {}", totalUnicorns, unicornArrangement.length());
            } else if (unicornArrangement.contains("BB")) {
                log.error("found BB in: {}", unicornArrangement);
            } else if (unicornArrangement.contains("RR")) {
                log.error("found RR in: {}", unicornArrangement);
            } else if (unicornArrangement.contains("YY")) {
                log.error("found YY in: {}", unicornArrangement);
            } else if (unicornArrangement.charAt(0) == unicornArrangement.charAt(unicornArrangement.length() - 1)) {
                log.error("bad wrap in: {}", unicornArrangement);
            }
        }
        return "Case #" + caze + ": " + unicornArrangement + "\n";
    }

    private String solveForUnicorns(List<Unicorn> unicorns) {
        Collections.sort(unicorns);
        log.info("starting unicorns: {}", unicorns);
        return allocateUnicorns(unicorns);
    }

    private String allocateUnicorns(List<Unicorn> unicorns) {
        log.info("Allocating: {}", unicorns);
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < unicorns.size() - 1; i++) {
            Unicorn unicorn = unicorns.get(i);
            int numPattensToAssign = unicorn.getRemaining();
            if (numPattensToAssign == 0) {
                log.info("nothing to allocate at {} : {}", i, unicorns);
                continue;
            }
            String currentPattern = makePattern(unicorns, i);
            log.info("allocation {} x {}", currentPattern, numPattensToAssign);
            for (int j = 0; j < numPattensToAssign; j++) {
                output.append(currentPattern);
            }
            updateUnicorns(unicorns, i, numPattensToAssign);
            log.info("result of allocation: {}\n{}", unicorns, output);
        }
        backfill(output, unicorns);
        return output.toString();
    }

    private String makePattern(List<Unicorn> unicorns, int i) {
        String s = "";
        Set<Character> exclude = (Set<Character>) exclusions.get(unicorns.get(i).getColor());
        for (int j = i; j < unicorns.size(); j++) {
            char color = unicorns.get(j).getColor();
            if (!exclude.contains(color)) {
                s += color;
                exclude.addAll((Collection<? extends Character>) exclusions.get(color));
            }
        }
        return s;
    }

    private void updateUnicorns(List<Unicorn> unicorns, int i, int numPattensToAssign) {
        for (int j = i; j < unicorns.size(); j++) {
            unicorns.get(j).assign(numPattensToAssign);
        }
    }

    private void backfill(StringBuilder output, List<Unicorn> unicorns) {
        Unicorn lastUnicorn = unicorns.get(unicorns.size() - 1);
        int remaining = lastUnicorn.getRemaining();
        String currentFullPattern = makePattern(unicorns, 0);
        StringBuilder originalPattern = new StringBuilder();
        StringBuilder newPattern = new StringBuilder();
        Set<Character> badNeighbors = (Set<Character>) exclusions.get(lastUnicorn.color);
        boolean filled = false;
        for (int i = 0; i < unicorns.size(); i++) {
            char color = unicorns.get(i).getColor();
            newPattern.append(color);
            originalPattern.append(color);
            int next = i + 1 < unicorns.size() ? i + 1 : 0;
            if (!filled &&
                    !badNeighbors.contains(color) &&
                    !badNeighbors.contains(unicorns.get(next).getColor())) {
                newPattern.append(lastUnicorn.color);
                filled = true;
            }
        }
        log.info("backfill: {} with {} x {}", currentFullPattern, newPattern, remaining);
        String ogPattern = originalPattern.toString();
        int replacementStartIndex = output.indexOf(ogPattern);
        int count = 0;
        while (replacementStartIndex != -1 && count < remaining) {
            count++;
            int endIndex = replacementStartIndex + originalPattern.length();
            output.replace(replacementStartIndex, endIndex, newPattern.toString());
            replacementStartIndex = output.indexOf(ogPattern, endIndex);
        }
    }

    @Data
    @AllArgsConstructor
    private static class Unicorn implements Comparable<Unicorn> {
        char color;
        int number;
        int assigned;

        public int getRemaining() {
            return number - assigned;
        }

        public void assign(int newNeighbors) {
            assigned += newNeighbors;
        }

        @Override
        public int compareTo(Unicorn o) {
            return number - o.number;
        }
    }
}
