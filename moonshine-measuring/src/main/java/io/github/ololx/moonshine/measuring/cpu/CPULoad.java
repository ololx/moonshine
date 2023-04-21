package io.github.ololx.moonshine.measuring.cpu;

import java.time.Duration;

public final class CPULoad {

    private static final int DEFAULT_CORES = Runtime.getRuntime().availableProcessors();

    public static final long PERCENTAGE = 100L;

    private final int cores;

    private final double load;

    private CPULoad(double load, int cores) {
        this.load = load;
        this.cores = cores;
    }

    public static CPULoad ofCPUTime(long cpuTime) {
        double load = cpuTime / (double) (Duration.ofSeconds(1).toNanos() * DEFAULT_CORES);

        return new CPULoad(load, DEFAULT_CORES);
    }

    public static CPULoad ofCPUTime(long cpuTime, Duration interval) {
        double load = cpuTime / (double) (interval.toNanos() * DEFAULT_CORES);

        return new CPULoad(load, DEFAULT_CORES);
    }

    public static CPULoad ofLoad(double load) {
        return new CPULoad(load, DEFAULT_CORES);
    }

    public static CPULoad ofLoad(double load, int cores) {
        return new CPULoad(load, DEFAULT_CORES);
    }

    public double toCPULoad() {
        return load;
    }

    public double toCPUUtilization() {
        return load * PERCENTAGE;
    }

    public long toCPUTime(Duration interval) {
        return (long) (load * cores * interval.toNanos());
    }

    public int getCores() {
        return cores;
    }

    @Override
    public String toString() {
        return String.format("%.2f%%", this.toCPUUtilization());
    }
}
