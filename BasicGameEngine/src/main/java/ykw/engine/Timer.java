package ykw.engine;

public class Timer {
    private long previousLoopTime;
    private long accumulator = 0;

    public void init() {
        previousLoopTime = currentTimeMillis();
        accumulator = 0;
    }

    public long currentTimeMillis() {
        return System.currentTimeMillis();
    }

    public long getAccumulator() {
        long currentTime = currentTimeMillis();
        long elapsedTime = currentTime - previousLoopTime;
        previousLoopTime = currentTime;
        accumulator += elapsedTime;
        return accumulator;
    }

    public void accumulatorDec(long dec) {
        this.accumulator -= dec;
    }

    public void setAccumulator(long accumulator) {
        this.accumulator = accumulator;
    }

    public long getPreviousLoopTime() {
        return previousLoopTime;
    }
}
