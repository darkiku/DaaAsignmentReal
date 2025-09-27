package org.example.Metrics;

import java.util.concurrent.atomic.AtomicLong;

public class MetricCounters {
    public final AtomicLong comp = new AtomicLong();
    public final AtomicLong alloc = new AtomicLong();
    public final DepthTracker depth = new DepthTracker();

    public void reset() {
        comp.set(0); alloc.set(0); depth.reset();
    }
}