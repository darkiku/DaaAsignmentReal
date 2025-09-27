package org.example.Sort;
import org.example.Metrics.MetricCounters;
import java.util.Arrays;

public class MergeSortX {
    private static final int INSERTION = 32;

    public static <T extends Comparable<? super T>> void sort(T[] a, MetricCounters m) {
        @SuppressWarnings("unchecked")
        T[] scratch = (T[]) new Comparable[a.length];
        rec(a, scratch, 0, a.length - 1, m);
    }

    private static <T extends Comparable<? super T>> void rec(T[] a, T[] scratch, int s, int e, MetricCounters m) {
        if (e - s + 1 <= INSERTION) {
            insertion(a, s, e, m);
            return;
        }
        m.depth.enter();
        int mid = (s + e) >>> 1;
        rec(a, scratch, s, mid, m);
        rec(a, scratch, mid+1, e, m);
        merge(a, scratch, s, mid, e, m);
        m.depth.exit();
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] scratch, int s, int mid, int e, MetricCounters m) {
        int i=s, j=mid+1, k=s;
        while (i<=mid && j<=e) {
            m.comp.incrementAndGet();
            if (a[i].compareTo(a[j]) <= 0) scratch[k++] = a[i++];
            else scratch[k++] = a[j++];
        }
        while (i<=mid) scratch[k++] = a[i++];
        while (j<=e) scratch[k++] = a[j++];
        System.arraycopy(scratch, s, a, s, e - s + 1);
        m.alloc.addAndGet( (e - s + 1) ); // грубая метрика
    }

    private static <T extends Comparable<? super T>> void insertion(T[] a, int s, int e, MetricCounters m) {
        for (int i=s+1;i<=e;i++){
            T val = a[i];
            int j = i-1;
            while (j>=s){ m.comp.incrementAndGet(); if (a[j].compareTo(val) > 0) { a[j+1]=a[j]; j--; } else break; }
            a[j+1] = val;
        }
    }
}

