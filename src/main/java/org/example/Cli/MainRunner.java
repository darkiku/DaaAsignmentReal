package org.example.Cli;

import org.example.Closest.ClosestPair2D;
import org.example.Metrics.CsvEmitter;
import org.example.Metrics.MetricCounters;
import org.example.Select.DetSelectZ;
import org.example.Sort.MergeSortX;
import org.example.Sort.QuickSortY;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public final class MainRunner {
    public static void main(String[] args) throws Exception {
        Map<String, String> arg = parseArgs(args);
        String algo = arg.getOrDefault("algo", "mergesort");
        int n = Integer.parseInt(arg.getOrDefault("n", "1000"));
        int trials = Integer.parseInt(arg.getOrDefault("trials", "3"));
        long seed = Long.parseLong(arg.getOrDefault("seed", String.valueOf(System.nanoTime())));
        String out = arg.getOrDefault("out", "results.csv");

        Random rnd = new Random(seed);
        Path outPath = Path.of(out);

        for (int t = 0; t < trials; t++) {
            MetricCounters m = new MetricCounters();
            long start = System.nanoTime();

            switch (algo.toLowerCase(Locale.ROOT)) {
                case "mergesort": {
                    Integer[] arr = rnd.ints(n, 0, n * 10).boxed().toArray(Integer[]::new);
                    MergeSortX.sort(arr, m);
                    break;
                }
                case "quicksort": {
                    Integer[] arr = rnd.ints(n, 0, n * 10).boxed().toArray(Integer[]::new);
                    QuickSortY.sort(arr, m);
                    break;
                }
                case "select": {
                    Integer[] arr = rnd.ints(n, 0, n * 10).boxed().toArray(Integer[]::new);
                    int k = n / 2;
                    DetSelectZ.select(arr, k, m);
                    break;
                }
                case "closest": {
                    ClosestPair2D.Point[] pts = new ClosestPair2D.Point[n];
                    for (int i = 0; i < n; i++) pts[i] = new ClosestPair2D.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
                    ClosestPair2D.solve(pts);
                    break;
                }
                default:
                    System.err.println("Unknown algo: " + algo);
                    return;
            }

            long took = System.nanoTime() - start;
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("algo", algo);
            row.put("n", n);
            row.put("trial", t);
            row.put("time_ns", took);
            row.put("comps", m.comp.get());
            row.put("alloc", m.alloc.get());
            row.put("depth", m.depth.maxDepth());
            try {
                CsvEmitter.append(outPath, row);
            } catch (IOException e) {
                System.err.println("failed to write csv: " + e.getMessage());
            }
            System.out.println("done trial " + t + " algo=" + algo + " n=" + n + " time_ns=" + took);
        }

        System.out.println("done all");
    }

    private static Map<String, String> parseArgs(String[] args) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            String s = args[i];
            if (s.startsWith("--")) {
                String key = s.substring(2);
                String val = "true";
                if (i + 1 < args.length && !args[i + 1].startsWith("--")) {
                    val = args[++i];
                }
                map.put(key, val);
            }
        }
        return map;
    }
}
