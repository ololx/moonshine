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

package io.github.ololx.moonshine.measuring.memory;

/**
 * Represents a memory size in bytes, and provides utility methods for
 * converting to different units of memory.
 *
 * <p>Instances of this class are immutable and are guaranteed to be
 * thread-safe.</p>
 *
 * @apiNote
 * The factory method {@link #ofBytes(long)} should be used to create instances
 * of this class. This method ensures that negative values are not accepted and
 * returns a cached instance for zero bytes.
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
    public static final long KILOBYTE = 1024L;

    /**
     * The number of bytes in 1 megabyte.
     */
    public static final long MEGABYTE = 1_048_576L;

    /**
     * The number of bytes in 1 gigabyte.
     */
    public static final long GIGABYTE = 1_073_741_824L;

    /**
     * The number of bytes in 1 terabyte.
     */
    public static final long TERABYTE = 1_099_511_627_776L;

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

    /**
     * Returns a new instance of {@code Memory} with the specified number of
     * kilobytes.
     *
     * @param kilobytes the number of kilobytes
     * @return a new instance of {@code Memory} with the specified number of
     * kilobytes
     */
    public static Memory ofKilobytes(long kilobytes) {
        return new Memory(kilobytes * KILOBYTE);
    }

    /**
     * Returns a new instance of {@code Memory} with the specified number of
     * megabytes.
     *
     * @param megabytes the number of megabytes
     * @return a new instance of {@code Memory} with the specified number of
     * megabytes
     */
    public static Memory ofMegabytes(long megabytes) {
        return new Memory(megabytes * MEGABYTE);
    }

    /**
     * Returns a new instance of {@code Memory} with the specified number of
     * gigabytes.
     *
     * @param gigabytes the number of megabytes
     * @return a new instance of {@code Memory} with the specified number of
     * gigabytes
     */
    public static Memory ofGigabytes(long gigabytes) {
        return new Memory(gigabytes * GIGABYTE);
    }

    /**
     * Returns a new instance of {@code Memory} with the specified number of
     * terabytes.
     *
     * @param terabytes the number of megabytes
     * @return a new instance of {@code Memory} with the specified number of
     * terabytes
     */
    public static Memory ofTerabytes(long terabytes) {
        return new Memory(terabytes * TERABYTE);
    }

    /**
     * Returns the number of bytes in this memory size.
     *
     * @return the number of bytes in this memory size
     */
    public long toBytes() {
        return this.bytes;
    }

    /**
     * Returns the number of kilobytes in this memory size.
     *
     * @return the number of kilobytes in this memory size
     */
    public long toKilobytes() {
        return this.bytes / KILOBYTE;
    }

    /**
     * Returns the number of megabytes in this memory size.
     *
     * @return the number of megabytes in this memory size
     */
    public long toMegabytes() {
        return this.bytes / MEGABYTE;
    }

    /**
     * Returns the number of gigabytes in this memory size.
     *
     * @return the number of gigabytes in this memory size
     */
    public long toGigabytes() {
        return this.bytes / GIGABYTE;
    }

    /**
     * Returns the number of terabytes in this memory size.
     *
     * @return the number of terabytes in this memory size
     */
    public long toTerabytes() {
        return this.bytes / TERABYTE;
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
    public int compareTo(Memory other) {
        return Long.compare(this.bytes, other.bytes);
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param obj the reference object with which to compare
     * @return true if this object is the same as the obj argument; false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Memory) {
            Memory other = (Memory) obj;
            return this.bytes == other.bytes;
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
        return Long.hashCode(this.bytes);
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
     * System.out.println("1 KB = " + kilobyte.toString());
     * }</pre>
     *
     * @return a string representation of this memory size, formatted in the
     * appropriate unit.
     */
    @Override
    public String toString() {
        if (this.bytes < KILOBYTE) {
            return this.bytes + " bytes";
        } else if (this.bytes < MEGABYTE) {
            return String.format("%.1f KB", this.bytes / (double) KILOBYTE);
        } else if (this.bytes < GIGABYTE) {
            return String.format("%.1f MB", this.bytes / (double) MEGABYTE);
        } else if (this.bytes < TERABYTE) {
            return String.format("%.1f GB", this.bytes / (double) GIGABYTE);
        } else {
            return String.format("%.1f TB", this.bytes / (double) TERABYTE);
        }
    }
}
