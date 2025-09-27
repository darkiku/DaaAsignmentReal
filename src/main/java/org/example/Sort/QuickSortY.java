package org.example.Sort;

import org.example.Metrics.MetricCounters;

import java.util.Random;

/**
 * QuickSort с randomized pivot, recurse on smaller partition (tail elimination).
 * Имя QuickSortY — немного отличено от примера.
 */
public class QuickSortY {

    private static final Random RND = new Random(0xC0FFEE);

    public static <T extends Comparable<? super T>> void sort(T[] a, MetricCounters m) {
        if (a == null || a.length <= 1) return;
        if (m == null) m = new MetricCounters();
        quick(a, 0, a.length - 1, m);
    }

    private static <T extends Comparable<? super T>> void quick(T[] a, int lo, int hi, MetricCounters m) {
        while (lo < hi) {
            m.depth.enter();
            int p = partition(a, lo, hi, m);
            int leftSize = p - lo;
            int rightSize = hi - p;
            if (leftSize < rightSize) {
                quick(a, lo, p - 1, m);
                lo = p + 1;
            } else {
                quick(a, p + 1, hi, m);
                hi = p - 1;
            }
            m.depth.exit();
        }
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int lo, int hi, MetricCounters m) {
        int pivotIdx = lo + RND.nextInt(hi - lo + 1);
        swap(a, pivotIdx, hi);
        T pivot = a[hi];
        int store = lo;
        for (int i = lo; i < hi; i++) {
            m.comp.incrementAndGet();
            if (a[i].compareTo(pivot) < 0) {
                swap(a, i, store++);
            }
        }
        swap(a, store, hi);
        return store;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }
}