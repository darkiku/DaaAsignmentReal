package org.example.Util;

import java.util.Random;

public final class ArrUtil {
    public static <T> void swap(T[] a, int i, int j) { T t=a[i]; a[i]=a[j]; a[j]=t; }
    public static void shuffle(int[] a, Random rnd) { for (int i = a.length-1; i>0; i--) { int j = rnd.nextInt(i+1); int t=a[i]; a[i]=a[j]; a[j]=t; } }
    public static void guardNotNull(Object o, String name){ if (o==null) throw new IllegalArgumentException(name+" is null"); }
}
