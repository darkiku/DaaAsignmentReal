package com.example.algorithms.Closest;

import org.example.Closest.ClosestPair2D;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClosestPair2DTest {

    private static double brute(ClosestPair2D.Point[] pts) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < pts.length; i++) {
            for (int j = i + 1; j < pts.length; j++) {
                double dx = pts[i].x - pts[j].x;
                double dy = pts[i].y - pts[j].y;
                min = Math.min(min, Math.sqrt(dx * dx + dy * dy));
            }
        }
        return min;
    }

    @Test
    void testSmallSet() {
        ClosestPair2D.Point[] pts = {
                new ClosestPair2D.Point(0, 0),
                new ClosestPair2D.Point(3, 4),
                new ClosestPair2D.Point(1, 1)
        };
        double fast = ClosestPair2D.solve(pts.clone());
        double brute = brute(pts.clone());
        assertEquals(brute, fast, 1e-9);
    }

    @Test
    void testRandomSets() {
        Random rnd = new Random(7);
        for (int n = 5; n <= 200; n += 50) {
            ClosestPair2D.Point[] pts = new ClosestPair2D.Point[n];
            for (int i = 0; i < n; i++) {
                pts[i] = new ClosestPair2D.Point(rnd.nextDouble() * 1000, rnd.nextDouble() * 1000);
            }
            double fast = ClosestPair2D.solve(pts.clone());
            double brute = brute(pts.clone());
            assertEquals(brute, fast, 1e-9);
        }
    }
}