package org.example.Metrics;
import java.util.concurrent.atomic.AtomicInteger;

public class DepthTracker {
    private final ThreadLocal<Integer> cur = ThreadLocal.withInitial(() -> 0);
    private final AtomicInteger max = new AtomicInteger(0);

    public void enter() {
        int d = cur.get() + 1;
        cur.set(d);
        max.updateAndGet(x -> Math.max(x, d));
    }
    public void exit() {
        cur.set(cur.get() - 1);
    }
    public int maxDepth() { return max.get(); }
    public void reset() { cur.set(0); max.set(0); }
}
