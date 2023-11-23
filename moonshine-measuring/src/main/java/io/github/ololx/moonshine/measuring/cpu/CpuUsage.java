package io.github.ololx.moonshine.measuring.cpu;

import java.time.Duration;

/**
 * The {@code CPUsage} class represents the CPU usage as a fraction, percentage
 * or duration.
 * The value is represented as a fraction of the total time available across
 * all cores.
 *
 * <p>Instances of this class are immutable and are guaranteed to be
 * thread-safe.</p>
 *
 * @author Alexander A. Kropotin
 * @apiNote <ol>
 *     <li>
 *     The usage fraction is a double value between 0.0 and 1.0
 *     representing the fraction of total CPU time used.
 *     </li>
 *     <li>
 *     The usage percent is a double value between 0.0 and 100.0
 *     representing the percentage of total CPU time used.
 *     </li>
 *     <li>
 *     The usage time is a Duration representing the time used by the process.
 *     </li>
 *     </ol>
 * @implSpec This implementation assumes that all cores of the system have the same
 *     capacity and the same speed. The CPU usage is calculated based on the total
 *     time used by the process during the specified interval and the total time
 *     available during that interval across all cores.
 * @implNote The CPU usage is calculated as follows:
 *     <ol>
 *         <li>(cpuTime.toNanos() / (interval.toNanos() * AVAILABLE_CORES))</li>
 *     </ol>
 *
 *     project moonshine
 *     created 13.04.2023 14:29
 */
public final class CpuUsage {

    /**
     * Constant for a CPU usage of zero.
     */
    public static final CpuUsage ZERO = new CpuUsage(0D);

    /**
     * The number of available cores in the system.
     */
    private static final int AVAILABLE_CORES = Runtime.getRuntime()
        .availableProcessors();

    /**
     * The percentage factor.
     */
    private static final long PERCENTAGE = 100L;

    /**
     * The CPU usage value.
     */
    private final double value;

    /**
     * Constructs a new {@code CPUsage} object with the specified value.
     *
     * @param value the CPU usage value.
     */
    private CpuUsage(double value) {
        this.value = value;
    }

    /**
     * Returns a new {@code CPUsage} object representing the CPU usage during
     * the specified interval.
     *
     * @param cpuTime  the CPU time used by the process during the specified
     *                 interval.
     * @param interval the duration of the interval.
     *
     * @return a new {@code CPUsage} object representing the CPU usage during
     *     the specified interval.
     *
     * @throws IllegalArgumentException if cpuTime or interval is negative or null.
     */
    public static CpuUsage ofUsageTime(Duration cpuTime, Duration interval) {
        checkDuration(cpuTime, "The cpuTime must be non-null and positive.");
        checkDuration(interval, "The interval must be non-null and positive.");

        return new CpuUsage(cpuTime.toNanos() / ((double) interval.toNanos() * AVAILABLE_CORES));
    }

    /**
     * Checks that the specified duration is not null or negative.
     *
     * @param duration the duration to check.
     *
     * @throws IllegalArgumentException if the duration is null.
     */
    private static void checkDuration(Duration duration, String message) {
        if (duration == null || duration.isNegative()) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Returns the CPU usage value as a fraction.
     *
     * @return the CPU usage value as a fraction.
     */
    public double toUsageFraction() {
        return value;
    }

    /**
     * Returns the CPU usage time for the specified interval.
     *
     * @param interval the duration of the interval.
     *
     * @return the CPU usage time for the specified interval.
     *
     * @throws IllegalArgumentException if interval is negative or null.
     */
    public Duration toUsageTime(Duration interval) {
        checkDuration(interval, "The interval must be non-null and positive.");

        return Duration.ofNanos((long) (value * (interval.toNanos() * AVAILABLE_CORES)));
    }

    /**
     * Returns a string representation of the CPU usage value as a percentage.
     *
     * @return a string representation of the CPU usage value as a percentage.
     */
    @Override
    public String toString() {
        return String.format("%.2f%%", this.toUsagePercent());
    }

    /**
     * Returns the CPU usage value as a percentage.
     *
     * @return the CPU usage value as a percentage.
     */
    public double toUsagePercent() {
        return value * PERCENTAGE;
    }
}
