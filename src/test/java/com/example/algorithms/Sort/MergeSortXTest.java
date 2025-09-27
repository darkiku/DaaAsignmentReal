package com.example.algorithms.Sort;
import org.example.Metrics.MetricCounters;
import org.example.Sort.MergeSortX;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class MergeSortXTest {

    @Test
    void testSmallArray() {
        Integer[] arr = {5, 2, 9, 1, 3};
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        MetricCounters m = new MetricCounters();
        MergeSortX.sort(arr, m);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testRandomArrays() {
        Random rnd = new Random(42);
        for (int n = 1; n <= 1000; n += 137) {
            Integer[] arr = rnd.ints(n, 0, 10000).boxed().toArray(Integer[]::new);
            Integer[] expected = arr.clone();
            Arrays.sort(expected);

            MetricCounters m = new MetricCounters();
            MergeSortX.sort(arr, m);

            assertArrayEquals(expected, arr);
        }
    }

    @Test
    void testAlreadySorted() {
        Integer[] arr = {1, 2, 3, 4, 5};
        Integer[] expected = arr.clone();

        MetricCounters m = new MetricCounters();
        MergeSortX.sort(arr, m);

        assertArrayEquals(expected, arr);
    }
}

