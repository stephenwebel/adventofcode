package com.webel.codejam.qualifier;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by Stephen Webel on 4/8/17.
 */
@Slf4j
public class TidyNumbers {
    public static void main(String[] args) throws Exception {
        TidyNumbers tn = new TidyNumbers();
        tn.solve();
    }

    private void solve() throws Exception {
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        List<String> tidyNumbers = getSortedTidyNumbers();

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
                    fw.write("Case #" + caze + ": " + findLastTiny(Long.parseLong(line), tidyNumbers) + "\n");
                    caze++;
                }
            }
            fw.close();
            log.info("Enter a new file path or exit: ");
            path = reader.next();
        }
    }

    private List<String> getSortedTidyNumbers() {
        Set<String> buildTidyNumbers = Sets.newHashSet("1", "2", "3", "4", "5", "6", "7", "8", "9");
        boolean notDone = true;
        while (notDone) {
            Set<String> newTidyNumbers = Sets.newHashSet();
            for (String tidyNumber : buildTidyNumbers) {
                if (tidyNumber.length() == 18) {
                    notDone = false;
                }
                int i = Integer.parseInt(tidyNumber.substring(tidyNumber.length() - 1));
                for (; i < 10; i++) {
                    String newNum = tidyNumber + i;
                    if (!newNum.equals("9999999999999999999")) {
                        newTidyNumbers.add(tidyNumber + i);
                    }
                }
            }
            buildTidyNumbers.addAll(newTidyNumbers);
        }
        List<String> tidyNumber = Lists.newArrayList(buildTidyNumbers);
        Collections.sort(tidyNumber, Comparator.comparingLong(Long::parseLong));
        return tidyNumber;
    }

    private String findLastTiny(long target, List<String> tidyNumbers) {
        int lo = 0;
        int hi = tidyNumbers.size() - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            Long m = Long.parseLong(tidyNumbers.get(mid));
            Long m1 = Long.parseLong(tidyNumbers.get(mid + 1));
            if (target == m) {
                return String.valueOf(m);
            } else if (m < target && target < m1) {
                return String.valueOf(m);
            } else if (m > target) {
                hi = mid - 1;
            } else {
                lo = mid + 1;
            }
        }
        log.error("danger");
        return null;
    }
}
