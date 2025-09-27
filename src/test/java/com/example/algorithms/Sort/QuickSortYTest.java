package com.example.algorithms.Sort;


import org.example.Metrics.MetricCounters;
import org.example.Sort.QuickSortY;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortYTest {

    @Test
    void testSmallArray() {
        Integer[] arr = {8, 3, 5, 1, 9, 2};
        Integer[] expected = arr.clone();
        Arrays.sort(expected);

        MetricCounters m = new MetricCounters();
        QuickSortY.sort(arr, m);

        assertArrayEquals(expected, arr);
    }

    @Test
    void testRandomArrays() {
        Random rnd = new Random(123);
        for (int n = 1; n <= 1000; n += 200) {
            Integer[] arr = rnd.ints(n, -1000, 1000).boxed().toArray(Integer[]::new);
            Integer[] expected = arr.clone();
            Arrays.sort(expected);

            MetricCounters m = new MetricCounters();
            QuickSortY.sort(arr, m);

            assertArrayEquals(expected, arr);
        }
    }

    @Test
    void testWithDuplicates() {
        Integer[] arr = {5, 5, 5, 5, 5};
        Integer[] expected = arr.clone();

        MetricCounters m = new MetricCounters();
        QuickSortY.sort(arr, m);

        assertArrayEquals(expected, arr);
    }
}