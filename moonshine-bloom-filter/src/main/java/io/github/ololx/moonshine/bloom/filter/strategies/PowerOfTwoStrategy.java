/**
 * Copyright 2024 the project moonshine authors
 * and the original author or authors annotated by {@author}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.bloom.filter.strategies;

import io.github.ololx.moonshine.bloom.filter.BloomFilter;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 28/04/2024 8:15pm
 */
public class PowerOfTwoStrategy implements BloomFilter.HashingStrategy {

    private final int size;

    public PowerOfTwoStrategy(final int size) {
        if (!isPowerOfTwo(size)) {
            throw new IllegalArgumentException("The bit collection must have elements count is power of two " + size);
        }

        this.size = size;
    }

    private static boolean isPowerOfTwo(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    /**
     * Aligns an index code to fit within the bounds of the bit array size.
     *
     * @param index the index code to be aligned.
     * @param size the size of the bit array.
     *
     * @return the index within the bounds of the bit array.
     *
     * @implSpec This method ensures that the index derived from the index code is within the bounds of the bit array
     *     by taking the absolute value of the index code modulo the size of the array.
     */
    private int align(final int index, final int size) {
        return (index & (size - 1));
    }

    /**
     * Calculates and returns the smallest power of two that is greater than or equal to the given integer.
     * If the given integer is already a power of two, it returns the same value. This method ensures that
     * the size of the bit array in the bloom filter is optimized for bitwise operations.
     *
     * @param x the integer for which to find the next power of two.
     *
     * @return the next power of two greater than or equal to {@code x}. If {@code x} is less than 1,
     *     the return value is 1, ensuring that the bit array has a positive size.
     *
     * @implNote This method operates by first decrementing {@code x} by 1 to handle the case where
     *     {@code x} is already a power of two. Then, it progressively sets all lower bits to 1
     *     by bitwise OR operations with right-shifted versions of {@code x}. Finally, it increments
     *     {@code x} by 1 to reach the next power of two. This approach ensures that the computation
     *     is efficient and only requires a logarithmic number of steps in the size of the integer.
     */
    public static int nextPowerOfTwo(int x) {
        if (x < 1) {
            return 1;
        }

        if ((x & (x - 1)) == 0) {
            return x;
        }

        x--;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        x++;

        return x;
    }

    @Override
    public int apply(final byte[] value, final BloomFilter.HashFunction hashFunction) {
        int index = hashFunction.apply(value);
        return align(index, size);
    }
}
