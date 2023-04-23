package io.github.ololx.moonshine.measuring.cpu;

import java.time.Duration;

public final class CPUsage {

    private static final int AVAILABLE_CORES = Runtime.getRuntime().availableProcessors();

    private static final long PERCENTAGE = 100L;

    private final double value;

    private CPUsage(double value) {
        this.value = value;
    }

    public static CPUsage ofUsageTime(Duration cpuTime, Duration interval) {
        return new CPUsage(cpuTime.toNanos() / ((double) interval.toNanos() * AVAILABLE_CORES));
    }

    public double toUsageFraction() {
        return value;
    }

    public double toUsagePercent() {
        return value * PERCENTAGE;
    }

    public Duration toUsageTime(Duration interval) {
        return Duration.ofNanos((long) (value * AVAILABLE_CORES * interval.toNanos()));
    }

    @Override
    public String toString() {
        return String.format("%.2f%%", this.toUsagePercent());
    }
}
