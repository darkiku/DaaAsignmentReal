package org.example.Closest;

import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair2D {
    public static double solve(Point[] pts) {
        Arrays.sort(pts, Comparator.comparingDouble(p->p.x));
        Point[] aux = pts.clone();
        return rec(pts, aux, 0, pts.length);
    }
    private static double rec(Point[] a, Point[] aux, int s, int e) { /* ... */ }
    // strip check: for each point i check up to 7 points after i in y-order
}
