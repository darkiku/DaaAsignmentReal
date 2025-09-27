package org.example.Sort;

import org.example.Metrics.MetricCounters;

import java.util.Random;

public class QuickSortY {
    private static final Random RND = new Random(0xC0FFEE);

    public static <T extends Comparable<? super T>> void sort(T[] a, MetricCounters m) {
        quick(a, 0, a.length - 1, m);
    }

    private static <T extends Comparable<? super T>> void quick(T[] a, int start, int end, MetricCounters m) {
        while (start < end) {
            m.depth.enter();
            int p = partition(a, start, end, m);
            int leftSize = p - start;
            int rightSize = end - p;
            if (leftSize < rightSize) {
                quick(a, start, p-1, m);
                start = p + 1;
            } else {
                quick(a, p+1, end, m);
                end = p - 1;
            }
            m.depth.exit();
        }
    }

    private static <T extends Comparable<? super T>> int partition(T[] a, int s, int e, MetricCounters m) {
        int pivotIdx = s + RND.nextInt(e - s + 1);
        swap(a, pivotIdx, e); // move pivot to end
        T pivot = a[e];
        int store = s;
        for (int i=s;i<e;i++){
            m.comp.incrementAndGet();
            if (a[i].compareTo(pivot) < 0) {
                swap(a, i, store++);
            }
        }
        swap(a, store, e);
        return store;
    }

    private static <T> void swap(T[] a, int i, int j) {
        T tmp = a[i]; a[i] = a[j]; a[j] = tmp;
    }
}
