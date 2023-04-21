/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <br/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <br/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <br/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.measuring.cpu;

import java.time.Duration;

/**
 * Represents processor load as percentage and provides utility methods for
 * converting to different formats.
 *
 * <p>Instances of this class are immutable and are guaranteed to be
 * thread-safe.</p>
 *
 * @implSpec
 * This class provides a total ordering based on the percentage of processor load, which is
 * consistent with {@link #equals(Object)}.
 * Specifically, if two processor loads are compared using
 * {@link #compareTo(CPULoadOld)}, the result will be:
 * <ul>
 *     <li>
 *         0 if and only if {@link #equals(Object)} would return true for those
 *         two loads
 *     </li>
 *     <li>a negative integer if this load is less than the other load</li>
 *     <li>a positive integer if this load is greater than the other load</li>
 * </ul>
 *
 * @apiNote
 * The factory method {@link #ofProcessorNano(long)} or {@link #ofProcessorNano(long, long)}
 * should be used to create instances of this class. The {@code ofProcessorNano(long)}
 * method ensures that the negative values are not accepted and returns a cached instance
 * for zero bytes.
 *
 * @implNote
 * This class uses the SI units for measuring memory size (i.e. 1 KB = 1000
 * bytes), rather than the binary prefixes (i.e. 1 KiB = 1024 bytes) that are
 * commonly used in computing. This is consistent with the International System
 * of Units (SI) and is becoming more common in software as well.
 *
 * project moonshine
 * created 01.04.2023 22:29
 *
 * @author Alexander A. Kropotin
 */
public final class CPULoadOld implements Comparable<CPULoadOld> {

    /**
     * The default interval in nanoseconds = 60 sec in nanoseconds
     */
    private static final long DEFAULT_INTERVAL_NANO = Duration.ofMinutes(1).toNanos();

    /**
     * The number of percentages in 100%.
     */
    private static final long PERCENTAGES = 100L;

    /**
     * The number of available processors.
     */
    private static final long AVAILABLE_PROCESSORS_COUNT = Runtime.getRuntime().availableProcessors();

    /**
     * The processor time that processor spent to work.
     */
    private final long processorTime;

    /**
     * The time interval - time for measurement.
     */
    private final long intervalTime;

    /**
     * Constructs a new instance of {@code ProcessorLoad} with the specified processor time
     * and default interval time = 60 sec.
     *
     * @param processorTime the processor time
     */
    private CPULoadOld(long processorTime) {
        this.processorTime = processorTime;
        this.intervalTime = DEFAULT_INTERVAL_NANO;
    }

    /**
     * Constructs a new instance of {@code ProcessorLoad} with the specified processor time
     * and interval time.
     *
     * @param processorTime the processor time
     * @param intervalTime the interval time
     */
    private CPULoadOld(long processorTime, long intervalTime) {
        this.processorTime = processorTime;
        this.intervalTime = intervalTime;
    }

    /**
     * Returns a new instance of {@code ProcessorLoad} with the specified processor time
     * and default interval time = 60 sec.
     *
     * @param processorTime the processor time
     * @return a new instance of {@code ProcessorLoad} with the specified processor time
     */
    public static CPULoadOld ofProcessorNano(long processorTime) {
        return new CPULoadOld(processorTime, DEFAULT_INTERVAL_NANO);
    }

    /**
     * Returns a new instance of {@code Memory} with the specified number of
     * bytes.
     *
     * @param processorTime the number of bytes
     * @return a new instance of {@code Memory} with the specified number of
     * bytes
     */
    public static CPULoadOld ofProcessorNano(long processorTime, long intervalTime) {
        return new CPULoadOld(processorTime, intervalTime);
    }

    /**
     * Returns the number of bytes in this memory size.
     *
     * @return the number of bytes in this memory size
     */
    public double toPercentage() {
        return intervalTime > 0
                ? (processorTime * PERCENTAGES) / (double) intervalTime / AVAILABLE_PROCESSORS_COUNT
                : 0;
    }

    /**
     * Compares this Memory object with the specified Memory objects for order
     * based on the number of bytes of memory they represent.
     *
     * @param other the Memory object to be compared
     * @return a negative integer, zero, or a positive integer as this Memory
     * object is less than, equal to, or greater than the specified Memory
     * object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     * from being compared to this Memory object.
     */
    @Override
    public int compareTo(CPULoadOld other) {
        return Double.compare(this.processorTime / (double) this.intervalTime, other.processorTime / (double) other.intervalTime);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof CPULoadOld) {
            CPULoadOld other = (CPULoadOld) obj;
            return this.processorTime == other.processorTime;
        }

        return false;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this Memory object
     */
    @Override
    public int hashCode() {
        return Long.hashCode(processorTime);
    }

    /**
     * Returns a string representation of this memory size.
     *
     * <p>If this memory size is less than one kilobyte, the result will be in
     * bytes. Otherwise, the result will be formatted in kilobytes, megabytes,
     * gigabytes or terabytes, depending on the magnitude of the memory size.
     *
     * @apiNote
     * This method does not perform any rounding or truncation of the memory
     * size value, so the returned string may contain fractional values.
     * <p><strong>For instance:</strong></p>
     * <ol>
     *     <li>
     *         If the memory size is 500 bytes, the result will be the string
     *         "500 bytes".
     *     </li>
     *     <li>
     *         If the memory size is 1.5 kilobytes, the result will be the
     *         string "1.5 KB".
     *     </li>
     *     <li>
     *        If the memory size is 750 megabytes, the result will be the
     *        string "750.0 MB".
     *     </li>
     * </ol>
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     * Memory kilobyte = Memory.ofBytes(1024);
     * System.out.println("1 KB = " + kilobyte.toString());
     * }</pre>
     *
     * @return a string representation of this memory size, formatted in the
     * appropriate unit.
     */
    @Override
    public String toString() {
        return String.format("%.2f%%", this.toPercentage());
    }
}
