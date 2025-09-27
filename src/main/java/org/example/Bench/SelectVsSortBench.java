package org.example.Bench;

import org.example.Metrics.MetricCounters;
import org.example.Select.DetSelectZ;
import org.openjdk.jmh.annotations.*;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SelectVsSortBench {

    @Param({"1000", "5000", "20000"})
    public int n;

    private Integer[] arr;

    @Setup(Level.Invocation)
    public void setUp() {
        Random rnd = new Random(123);
        arr = rnd.ints(n, 0, n * 10).boxed().toArray(Integer[]::new);
    }

    @Benchmark
    public Integer benchSelect() {
        MetricCounters m = new MetricCounters();
        return DetSelectZ.select(arr.clone(), arr.length/2, m);
    }

    @Benchmark
    public Integer benchSort() {
        Integer[] c = arr.clone();
        Arrays.sort(c);
        return c[c.length/2];
    }
}
