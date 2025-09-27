package org.example.Select;

import org.example.Metrics.MetricCounters;

import java.util.Arrays;

public class DetSelectZ {

    //returns k element(0 based) the minimums of element of list
    public static <T extends Comparable<? super T>> T select(T[] arr, int k, MetricCounters m) {
        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException("Array is empty");
        if (k < 0 || k >= arr.length)
            throw new IllegalArgumentException("Index out of range: " + k);

        return selectRec(arr, 0, arr.length - 1, k, m);
    }

    private static <T extends Comparable<? super T>> T selectRec(T[] arr, int left, int right, int k, MetricCounters m) {
        while (true) {
            if (left == right) return arr[left];

            m.depth.enter();
            int pivotIndex = medianOfMedians(arr, left, right, m);
            pivotIndex = partition(arr, left, right, pivotIndex, m);

            if (k == pivotIndex) {
                m.depth.exit();
                return arr[k];
            } else if (k < pivotIndex) {
                right = pivotIndex - 1;
            } else {
                left = pivotIndex + 1;
            }
            m.depth.exit();
        }
    }

    private static <T extends Comparable<? super T>> int partition(T[] arr, int left, int right, int pivotIndex, MetricCounters m) {
        T pivotValue = arr[pivotIndex];
        swap(arr, pivotIndex, right);
        int store = left;
        for (int i = left; i < right; i++) {
            m.comp.incrementAndGet();
            if (arr[i].compareTo(pivotValue) < 0) {
                swap(arr, store, i);
                store++;
            }
        }
        swap(arr, right, store);
        return store;
    }

    private static <T extends Comparable<? super T>> int medianOfMedians(T[] arr, int left, int right, MetricCounters m) {
        int n = right - left + 1;
        if (n < 5) {
            Arrays.sort(arr, left, right + 1);
            return (left + right) >>> 1;
        }

        int numMedians = (int) Math.ceil(n / 5.0);
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            int medianIdx = (subLeft + subRight) >>> 1;
            swap(arr, left + i, medianIdx);
        }

        return medianOfMedians(arr, left, left + numMedians - 1, m);
    }

    private static <T> void swap(T[] arr, int i, int j) {
        T tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}