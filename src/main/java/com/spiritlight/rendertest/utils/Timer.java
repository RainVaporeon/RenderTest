package com.spiritlight.rendertest.utils;

public class Timer {

    private final Runnable command;

    public Timer(Runnable command) {
        this.command = command;
    }

    public long run(int repeat) {
        long total = 0;
        for(int i = 0; i < repeat; i++) {
            total += run();
        }
        return total;
    }

    public long run() {
        final long time = System.currentTimeMillis();
        command.run();
        final long finish = System.currentTimeMillis();
        return finish - time;
    }
}
