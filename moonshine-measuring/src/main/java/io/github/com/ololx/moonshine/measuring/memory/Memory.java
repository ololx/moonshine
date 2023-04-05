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
 * project moonshine
 * created 01.04.2023 22:29
 *
 * @author Alexander A. Kropotin
 */
public final class Memory implements Comparable<Memory> {

    private static final long KILOBYTE = 1024L;

    private static final long MEGABYTE = 1024L * KILOBYTE;

    private static final long GIGABYTE = 1024L * MEGABYTE;

    private static final long TERABYTE = 1024L * GIGABYTE;

    private final long bytes;

    private Memory(long bytes) {
        this.bytes = bytes;
    }

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

    public long toBytes() {
        return bytes;
    }

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

    public Memory plus(Memory other) {
        return new Memory(this.bytes + other.bytes);
    }

    public Memory minus(Memory other) {
        return new Memory(this.bytes - other.bytes);
    }

    public Memory multipliedBy(long multiplicand) {
        return new Memory(this.bytes * multiplicand);
    }

    public Memory dividedBy(long divisor) {
        return new Memory(this.bytes / divisor);
    }

    public Memory negated() {
        return new Memory(-this.bytes);
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

