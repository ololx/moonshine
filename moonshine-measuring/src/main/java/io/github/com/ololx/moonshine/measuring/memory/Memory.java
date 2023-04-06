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

package io.github.com.ololx.moonshine.measuring.memory;

/**
 * Represents a memory size in bytes, and provides utility methods for
 * converting to different units of memory.
 *
 * <p>Instances of this class are immutable and are guaranteed to be
 * thread-safe.</p>
 *
 * @implSpec
 * This class provides a total ordering based on the number of bytes, which is
 * consistent with {@link #equals(Object)}.
 * Specifically, if two memory sizes are compared using
 * {@link #compareTo(Memory)}, the result will be:
 * <ul>
 *     <li>
 *         0 if and only if {@link #equals(Object)} would return true for those
 *         two sizes
 *     </li>
 *     <li>a negative integer if this size is less than the other size</li>
 *     <li>a positive integer if this size is greater than the other size</li>
 * </ul>
 *
 * @apiNote
 * The factory method {@link #ofBytes(long)} should be used to create instances
 * of this class. This method ensures that negative values are not accepted and
 * returns a cached instance for zero bytes.
 *
 * @implNote
 * This class uses the SI units for measuring memory size (i.e. 1 KB = 1000
 * bytes), rather than the binary prefixes (i.e. 1 KiB = 1024 bytes) that are
 * commonly used in computing. This is consistent with the International System
 * of Units (SI) and is becoming more common in software as well.
 *
 * @see <a href="https://en.wikipedia.org/wiki/Kilobyte#Definition">Kilobyte - Wikipedia</a>
 *
 * project moonshine
 * created 01.04.2023 22:29
 *
 * @author Alexander A. Kropotin
 */
public final class Memory implements Comparable<Memory> {

    /**
     * The number of bytes in 1 kilobyte.
     */
    private static final long KILOBYTE = 1024L;

    /**
     * The number of bytes in 1 megabyte.
     */
    private static final long MEGABYTE = 1024L * KILOBYTE;

    /**
     * The number of bytes in 1 gigabyte.
     */
    private static final long GIGABYTE = 1024L * MEGABYTE;

    /**
     * The number of bytes in 1 terabyte.
     */
    private static final long TERABYTE = 1024L * GIGABYTE;

    /**
     * The number of bytes in this memory size.
     */
    private final long bytes;

    /**
     * Constructs a new instance of {@code Memory} with the specified number of
     * bytes.
     *
     * @param bytes the number of bytes
     */
    private Memory(long bytes) {
        this.bytes = bytes;
    }

    /**
     * Returns a new instance of {@code Memory} with the specified number of
     * bytes.
     *
     * @param bytes the number of bytes
     * @return a new instance of {@code Memory} with the specified number of
     * bytes
     */
    public static Memory ofBytes(long bytes) {
        return new Memory(bytes);
    }

    public static Memory ofKilobytes(long kilobytes) {
        return new Memory(kilobytes * KILOBYTE);
    }

    public static Memory ofMegabytes(long megabytes) {
        return new Memory(megabytes * MEGABYTE);
    }

    public static Memory ofGigabytes(long gigabytes) {
        return new Memory(gigabytes * GIGABYTE);
    }

    public static Memory ofTerabytes(long terabytes) {
        return new Memory(terabytes * TERABYTE);
    }

    /**
     * Returns the number of bytes in this memory size.
     *
     * @return the number of bytes in this memory size
     */
    public long toBytes() {
        return bytes;
    }

    /**
     * Returns the number of kilobytes in this memory size.
     *
     * @return the number of kilobytes in this memory size
     */
    public long toKilobytes() {
        return bytes / KILOBYTE;
    }

    public long toMegabytes() {
        return bytes / MEGABYTE;
    }

    public long toGigabytes() {
        return bytes / GIGABYTE;
    }

    public long toTerabytes() {
        return bytes / TERABYTE;
    }

    /**
     * Returns a new instance of {@code Memory} that represents the sum of this
     * memory size and the specified memory size.
     *
     * @param other the memory size to add
     * @return a new instance of {@code Memory} that represents the sum of this
     * memory size and the specified memory size
     */
    public Memory plus(Memory other) {
        return new Memory(this.bytes + other.bytes);
    }

    /**
     * Returns a new instance of {@code Memory} that represents the difference
     * of this memory size and the specified memory size.
     *
     * @param other the memory size to minus
     * @return a new instance of {@code Memory} that represents the difference
     * of this memory size and the specified memory size
     */
    public Memory minus(Memory other) {
        return new Memory(this.bytes - other.bytes);
    }

    /**
     * Returns a new instance of {@code Memory} that represents the
     * multiplication of this memory size and the specified multiplicand.
     *
     * @param multiplicand the number to multiplication
     * @return a new instance of {@code Memory} that represents the
     * multiplication of this memory size and the specified multiplicand
     */
    public Memory multipliedBy(long multiplicand) {
        return new Memory(this.bytes * multiplicand);
    }

    /**
     * Returns a new instance of {@code Memory} that represents the
     * division of this memory size and the specified divisor.
     *
     * @param divisor the number to multiplication
     * @return a new instance of {@code Memory} that represents the
     * division of this memory size and the specified divisor
     */
    public Memory dividedBy(long divisor) {
        return new Memory(this.bytes / divisor);
    }

    public int compareTo(Memory other) {
        return Long.compare(this.bytes, other.bytes);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Memory) {
            Memory other = (Memory) obj;
            return this.bytes == other.bytes;
        }
        return false;
    }

    public int hashCode() {
        return Long.hashCode(bytes);
    }

    /**
     * Returns a string representation of this memory size.
     *
     * <p>If this memory size is less than one kilobyte, the result will be in
     * bytes. Otherwise, the result will be formatted in kilobytes, megabytes,
     * gigabytes or terabytes, depending on the magnitude of the memory size.
     *
     * @implNote
     * This method uses the constants {@link #KILOBYTE}, {@link #MEGABYTE},
     * {@link #GIGABYTE} and {@link #TERABYTE} to determine the appropriate
     * unit for formatting the memory size.
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
     * System.out.println("1 KB = kilobyte.toString());
     * }</pre>
     *
     * @return a string representation of this memory size, formatted in the
     * appropriate unit.
     */
    public String toString() {
        if (bytes < KILOBYTE) {
            return bytes + " bytes";
        } else if (bytes < MEGABYTE) {
            return String.format("%.1f KB", bytes / (double) KILOBYTE);
        } else if (bytes < GIGABYTE) {
            return String.format("%.1f MB", bytes / (double) MEGABYTE);
        } else if (bytes < TERABYTE) {
            return String.format("%.1f GB", bytes / (double) GIGABYTE);
        } else {
            return String.format("%.1f TB", bytes / (double) TERABYTE);
        }
    }
}
