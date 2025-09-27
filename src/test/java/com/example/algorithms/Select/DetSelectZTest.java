package com.example.algorithms.Select;

import org.example.Metrics.MetricCounters;
import org.example.Select.DetSelectZ;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DetSelectZTest {

    @Test
    void testSmallArray() {
        Integer[] arr = {9, 1, 7, 3, 5};
        for (int k = 0; k < arr.length; k++) {
            Integer[] copy = arr.clone();
            Arrays.sort(copy);
            MetricCounters m = new MetricCounters();
            Integer result = DetSelectZ.select(arr.clone(), k, m);
            assertEquals(copy[k], result);
        }
    }

    @Test
    void testRandomArrays() {
        Random rnd = new Random(99);
        for (int n = 5; n <= 100; n += 25) {
            Integer[] arr = rnd.ints(n, 0, 1000).boxed().toArray(Integer[]::new);
            Integer[] sorted = arr.clone();
            Arrays.sort(sorted);
            for (int k = 0; k < n; k++) {
                MetricCounters m = new MetricCounters();
                Integer result = DetSelectZ.select(arr.clone(), k, m);
                assertEquals(sorted[k], result);
            }
        }
    }
}