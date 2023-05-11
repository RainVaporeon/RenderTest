package com.spiritlight.rendertest.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AutoScaler {
    private final int interval;
    private final TimeUnit unit;

    private final IncrementManager manager;
    private ScheduledExecutorService executor;

    public AutoScaler(AtomicInteger field, int max, int min, int difference, int interval, TimeUnit timeUnit) {
        this.interval = interval;
        this.manager = new IncrementManager(null, field, max, min, difference);
        this.unit = timeUnit;
    }

    public AutoScaler(AtomicInteger field, int max, int min, int difference, int interval, TimeUnit timeUnit, Runnable updateAction) {
        this.interval = interval;
        this.manager = new IncrementManager(updateAction, field, max, min, difference);
        this.unit = timeUnit;
    }

    public void start() {
        if(executor == null || executor.isShutdown()) {
            executor = Executors.newSingleThreadScheduledExecutor();
            executor.scheduleAtFixedRate(manager, 0, interval, unit);
        }
    }

    public void stop() {
        executor.shutdownNow();
    }

    private static class IncrementManager implements Runnable {

        private final int max, min, diff;
        private final AtomicInteger value;
        private int cursor;
        private boolean mode = false;
        private final Runnable action;

        private IncrementManager(Runnable action, AtomicInteger value, int max, int min, int diff) {
            this.max = max;
            this.min = min;
            this.diff = diff;
            this.value = value;
            this.action = action;
            cursor = 0;
        }

        @Override
        public void run() {
            if(cursor > max) mode = true;
            if(cursor < min) mode = false;
            cursor += mode ? -diff : diff;
            value.set(cursor);
            if(action != null) action.run();
        }

    }
}
