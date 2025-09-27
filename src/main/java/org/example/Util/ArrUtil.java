package org.example.Util;
import java.util.Random;
import java.util.Objects;

/**
 * Утилиты: exch (swap), shuffle (Fisher-Yates), guardNotNull.
 * И немного альтернативных имён, чтобы отличаться от чужого репо.
 */
public final class ArrUtil {
    private ArrUtil() {}

    public static <T> void exch(T[] a, int i, int j) {
        T tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static void exch(int[] a, int i, int j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public static <T> void shuffle(T[] a, Random rnd) {
        Objects.requireNonNull(a);
        if (rnd == null) rnd = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            exch(a, i, j);
        }
    }

    public static void shuffle(int[] a, Random rnd) {
        Objects.requireNonNull(a);
        if (rnd == null) rnd = new Random();
        for (int i = a.length - 1; i > 0; i--) {
            int j = rnd.nextInt(i + 1);
            exch(a, i, j);
        }
    }

    public static void guardNotNull(Object o, String name) {
        if (o == null) throw new IllegalArgumentException(name + " must not be null");
    }

    public static void guardIndex(int idx, int len, String name) {
        if (idx < 0 || idx >= len) throw new IndexOutOfBoundsException(name + " out of bounds");
    }
}