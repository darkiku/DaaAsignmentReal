package org.example.Closest;

import java.util.Arrays;
import java.util.Comparator;


public final class ClosestPair2D {
    private ClosestPair2D() {}

    public static class Point {
        public final double x, y;
        public Point(double x, double y) { this.x = x; this.y = y; }
    }

    public static double solve(Point[] pts) {
        if (pts == null || pts.length < 2) throw new IllegalArgumentException("need at least 2 points");
        Point[] byX = pts.clone();
        Arrays.sort(byX, Comparator.comparingDouble(p -> p.x));
        Point[] aux = new Point[pts.length];
        return rec(byX, aux, 0, pts.length);
    }

    private static double rec(Point[] pts, Point[] aux, int l, int r) {
        int n = r - l;
        if (n <= 3) {
            double best = brute(pts, l, r);
            Arrays.sort(pts, l, r, Comparator.comparingDouble(p -> p.y));
            return best;
        }

        int mid = l + n / 2;
        double midX = pts[mid].x;
        double dL = rec(pts, aux, l, mid);
        double dR = rec(pts, aux, mid, r);
        double d = Math.min(dL, dR);

        // merge by y into aux
        int i = l, j = mid, k = 0;
        while (i < mid && j < r) {
            if (pts[i].y <= pts[j].y) aux[k++] = pts[i++];
            else aux[k++] = pts[j++];
        }
        while (i < mid) aux[k++] = pts[i++];
        while (j < r) aux[k++] = pts[j++];
        System.arraycopy(aux, 0, pts, l, k);

        // collect strip (use aux area as scratch)
        int m = 0;
        for (int t = l; t < r; t++) {
            if (Math.abs(pts[t].x - midX) < d) aux[m++] = pts[t];
        }
        // check at most next 7 neighbors
        for (int p = 0; p < m; p++) {
            for (int q = p + 1; q < m && (aux[q].y - aux[p].y) < d; q++) {
                d = Math.min(d, dist(aux[p], aux[q]));
            }
        }
        return d;
    }

    private static double brute(Point[] pts, int l, int r) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = l; i < r; i++) {
            for (int j = i + 1; j < r; j++) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double dist(Point a, Point b) {
        double dx = a.x - b.x;
        double dy = a.y - b.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
}