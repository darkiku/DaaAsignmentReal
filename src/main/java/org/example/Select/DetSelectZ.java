package org.example.Select;

import org.example.Metrics.MetricCounters;
import org.example.Util.ArrUtil;

import java.util.Arrays;

public final class DetSelectZ {
    private DetSelectZ() {}

    public static <T extends Comparable<? super T>> T select(T[] arr, int k, MetricCounters m) {
        if (arr == null) throw new IllegalArgumentException("arr is null");
        if (k < 0 || k >= arr.length) throw new IllegalArgumentException("k out of range");
        if (m == null) m = new MetricCounters();
        return selectRec(arr, 0, arr.length - 1, k, m);
    }

    private static <T extends Comparable<? super T>> T selectRec(T[] arr, int left, int right, int k, MetricCounters m) {
        while (left <= right) {
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
        throw new IllegalStateException("unreachable");
    }

    private static <T extends Comparable<? super T>> int partition(T[] arr, int left, int right, int pivotIndex, MetricCounters m) {
        T pivotValue = arr[pivotIndex];
        ArrUtil.exch(arr, pivotIndex, right);
        int store = left;
        for (int i = left; i < right; i++) {
            m.comp.incrementAndGet();
            if (arr[i].compareTo(pivotValue) < 0) {
                ArrUtil.exch(arr, store, i);
                store++;
            }
        }
        ArrUtil.exch(arr, store, right);
        return store;
    }

    private static <T extends Comparable<? super T>> int medianOfMedians(T[] arr, int left, int right, MetricCounters m) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return left + (n - 1) / 2;
        }

        int numGroups = (n + 4) / 5;
        for (int i = 0; i < numGroups; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            int medianIdx = subLeft + (subRight - subLeft) / 2;
            ArrUtil.exch(arr, left + i, medianIdx);
        }
        return medianOfMedians(arr, left, left + numGroups - 1, m);
    }
}