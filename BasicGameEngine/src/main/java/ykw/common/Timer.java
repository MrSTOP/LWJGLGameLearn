package ykw.common;

public class Timer {
    private long previousLoopTime;
    private long accumulator = 0;

    public void init() {
        previousLoopTime = getTime();
    }

    public long getTime() {
        return System.currentTimeMillis();
    }

    public long getAccumulator() {
        accumulator += getElapsedTime();
        return accumulator;
    }

    public void accumulatorDec(long dec) {
        this.accumulator -= dec;
    }

    public void setAccumulator(long accumulator) {
        this.accumulator = accumulator;
    }

    public long getElapsedTime() {
        long currentTime = getTime();
        long elapsedTime = currentTime - previousLoopTime;
        previousLoopTime = currentTime;
        return elapsedTime;
    }

    public long getPreviousLoopTime() {
        return previousLoopTime;
    }
}
