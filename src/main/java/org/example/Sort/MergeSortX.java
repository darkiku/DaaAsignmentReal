package org.example.Sort;
import org.example.Metrics.MetricCounters;
import java.util.Arrays;

public class MergeSortX {

    private static final int INSERTION_CUTOFF = 32;

    public static <T extends Comparable<? super T>> void sort(T[] a, MetricCounters m) {
        if (a == null || a.length <= 1) return;
        if (m == null) m = new MetricCounters();
        @SuppressWarnings("unchecked")
        T[] scratch = (T[]) new Comparable[a.length];
        rec(a, scratch, 0, a.length - 1, m);
    }

    private static <T extends Comparable<? super T>> void rec(T[] a, T[] scratch, int lo, int hi, MetricCounters m) {
        if (lo >= hi) return;
        int len = hi - lo + 1;
        if (len <= INSERTION_CUTOFF) {
            insertion(a, lo, hi, m);
            return;
        }
        int mid = (lo + hi) >>> 1;
        m.depth.enter();
        rec(a, scratch, lo, mid, m);
        rec(a, scratch, mid + 1, hi, m);
        merge(a, scratch, lo, mid, hi, m);
        m.depth.exit();
    }

    private static <T extends Comparable<? super T>> void merge(T[] a, T[] scratch, int lo, int mid, int hi, MetricCounters m) {
        int i = lo, j = mid + 1, k = lo;
        while (i <= mid && j <= hi) {
            m.comp.incrementAndGet();
            if (a[i].compareTo(a[j]) <= 0) {
                scratch[k++] = a[i++];
            } else {
                scratch[k++] = a[j++];
            }
        }
        while (i <= mid) scratch[k++] = a[i++];
        while (j <= hi) scratch[k++] = a[j++];
        System.arraycopy(scratch, lo, a, lo, hi - lo + 1);
        m.alloc.addAndGet(hi - lo + 1);
    }

    private static <T extends Comparable<? super T>> void insertion(T[] a, int lo, int hi, MetricCounters m) {
        for (int i = lo + 1; i <= hi; i++) {
            T val = a[i];
            int j = i - 1;
            while (j >= lo) {
                m.comp.incrementAndGet();
                if (a[j].compareTo(val) > 0) {
                    a[j + 1] = a[j];
                    j--;
                } else break;
            }
            a[j + 1] = val;
        }
    }
}