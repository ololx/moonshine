/**
 * Copyright 2023 the project moonshine authors
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

package io.github.ololx.moonshine.util.concurrent;

import io.github.ololx.moonshine.util.concurrent.atomic.AtomicByteArray;

import java.util.function.IntUnaryOperator;

/**
 * A thread-safe implementation of a {@link io.github.ololx.moonshine.util.concurrent.ConcurrentBitCollection} using
 * atomic operations.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 01.08.2023 10:52
 * @apiNote This class provides methods to manipulate individual bits in a thread-safe manner.
 *     It is designed for scenarios where multiple threads need to access and modify
 *     a shared bitset concurrently.
 */
public class ConcurrentBitArray implements ConcurrentBitCollection {

    /**
     * The size of a word in bits.
     */
    private static final int WORD_SIZE = Byte.SIZE;

    /**
     * The number of bits required to address a specific position within a "word."
     * Each "word" corresponds to a long (64 bits), so 6 bits are needed.
     */
    private static final int WORD_INDEX_SHIFT = 3;

    /**
     * The bits in byte word to cut int to byte.
     */
    private static final int WORD_MASK = 0x7;

    /**
     * A utility function that extracts the bit index within a word for a given bit index.
     * The result ensures the bit index is within the bounds of a word.
     */
    private static final IntUnaryOperator bitIndexShiftOperator = bitIndex -> bitIndex & WORD_MASK;

    /**
     * A utility function that returns a mask with only one bit set, corresponding to the given bit shift.
     * This mask can be used in bitwise operations to isolate or modify a specific bit.
     */
    private static final IntUnaryOperator bitMaskOperator = bitShift -> 1 << bitShift;

    /**
     * A utility function that calculates the word index (e.g., in an array of integers) for a given bit index.
     * This can be useful in scenarios where bits are managed in groups or words,
     * and a specific bit index needs to be mapped to its corresponding word.
     */
    private static final IntUnaryOperator wordIndexOperator = bitIndex -> bitIndex >> WORD_INDEX_SHIFT;

    /**
     * The internal storage for the bitset.
     */
    private final AtomicByteArray data;

    /**
     * The max index which can be used.
     */
    private final int lastBitIndex;

    /**
     * Creates a new NonBlockingConcurrentBitset with the specified size.
     *
     * @param size The number of bits in the bitset.
     */
    public ConcurrentBitArray(int size) {
        this.data = new AtomicByteArray((size + WORD_SIZE - 1) / WORD_SIZE);
        this.lastBitIndex = size - 1;
    }

    /**
     * Creates a new ConcurrentBitArray using the provided byte array.
     *
     * @param words The byte array representing the initial state of the bit array.
     */
    private ConcurrentBitArray(final byte[] words) {
        // put origin value because words.clone() will be invoked inside AtomicByteArray constructor
        this.data = new AtomicByteArray(words);
        this.lastBitIndex = words.length * WORD_SIZE - 1;
    }

    /**
     * Returns a new instance of ConcurrentBitArray using the provided byte array.
     *
     * @param bytes The byte array representing the initial state of the bit array.
     *
     * @return A new instance of ConcurrentBitArray initialized with the given byte array.
     */
    public static ConcurrentBitArray valueOf(final byte[] bytes) {
        return new ConcurrentBitArray(bytes);
    }

    /**
     * Gets the value of the bit at the specified index.
     *
     * @param bitIndex The index of the bit to retrieve.
     *
     * @return The value of the bit (true or false) at the specified index.
     */
    @Override
    public boolean get(int bitIndex) {
        checkIndex(bitIndex);
        int bitOffset = bitIndexShiftOperator.applyAsInt(bitIndex);
        int bitMask = bitMaskOperator.applyAsInt(bitOffset);
        return (this.data.get(wordIndexOperator.applyAsInt(bitIndex)) & bitMask) >> bitOffset != 0;
    }

    /**
     * Sets the bit at the specified index to 1.
     *
     * @param bitIndex The index of the bit to set.
     */
    @Override
    public void set(int bitIndex) {
        checkIndex(bitIndex);
        int bitOffset = bitIndexShiftOperator.applyAsInt(bitIndex);
        int bitMask = bitMaskOperator.applyAsInt(bitOffset);
        this.data.getAndBitwiseOr(wordIndexOperator.applyAsInt(bitIndex), (byte) bitMask);
    }

    /**
     * Clears the bit at the specified index (sets it to 0).
     *
     * @param bitIndex The index of the bit to clear.
     */
    @Override
    public void clear(int bitIndex) {
        checkIndex(bitIndex);
        int bitOffset = bitIndexShiftOperator.applyAsInt(bitIndex);
        int bitMask = ~bitMaskOperator.applyAsInt(bitOffset);
        this.data.getAndBitwiseAnd(wordIndexOperator.applyAsInt(bitIndex), (byte) bitMask);
    }

    /**
     * Flips the value of the bit at the specified index (0 becomes 1, and 1 becomes 0).
     *
     * @param bitIndex The index of the bit to flip.
     */
    @Override
    public void flip(int bitIndex) {
        checkIndex(bitIndex);
        int bitOffset = bitIndexShiftOperator.applyAsInt(bitIndex);
        int bitMask = bitMaskOperator.applyAsInt(bitOffset);
        this.data.getAndBitwiseXor(wordIndexOperator.applyAsInt(bitIndex), (byte) bitMask);
    }

    /**
     * Returns the number of bits in the collection that are set to 1. This is often referred to as the
     * <a href="https://en.wikipedia.org/wiki/Hamming_weight">Hamming weight</a> or pop count.
     *
     * @return The number of bits set to 1.
     *
     * @implSpec This implementation iterates over each byte in the internal {@code AtomicByteArray}
     *     and uses a precomputed lookup table (from the {@code BitCounting} utility class) to determine
     *     the number of set bits in each byte. The sum of all set bits across all bytes is returned as the result.
     */
    @Override
    public int cardinality() {
        int wordsCount = data.length();
        int cardinality = 0;

        for (int index = 0; index < wordsCount; index++) {
            cardinality += ByteBitCounting.bitCount(data.get(index));
        }

        return cardinality;
    }

    /**
     * Checks if the provided bit index is within the valid range of the data array.
     * The valid range is [0, bitsCount), where bitsCount is the total number of bits in the data array.
     * If the provided bit index is outside the acceptable range, this method throws an
     * {@link IndexOutOfBoundsException}.
     *
     * @param bitIndex The bit index to check.
     *
     * @throws IndexOutOfBoundsException If the bitIndex is negative or greater than or equal to the total number of
     *                                   bits in the data array.
     */
    private void checkIndex(final int bitIndex) {
        if (bitIndex < 0 || bitIndex > lastBitIndex) {
            throw new IndexOutOfBoundsException(String.format(
                "The bitIndex %s out of bounds [%s, %s]", bitIndex, 0, lastBitIndex
            ));
        }
    }

    /**
     * A utility class to help count the number of bits set in a byte.
     */
    public static final class ByteBitCounting {

        /**
         * Returns the number of one-bits in the two's complement binary representation
         * of the specified {@code byte} value.
         *
         * @param bits the value whose bits are to be counted
         *
         * @return the number of one-bits in the specified value
         */
        public static int bitCount(byte bits) {
            int count = bits & 0xFF;
            count = count - ((count >>> 1) & 0x55);
            count = (count & 0x33) + ((count >>> 2) & 0x33);
            count = (count + (count >>> 4)) & 0x0F;
            return count;
        }
    }

    public static final class ByteBitCountingLoUp {

        private static final int[] BIT_COUNT_LOOKUP = new int[256];

        static {
            for (int i = 0; i < 256; i++) {
                BIT_COUNT_LOOKUP[i] = (i & 1) + BIT_COUNT_LOOKUP[i >>> 1];
            }
        }

        public static int bitCount(byte bits) {
            return BIT_COUNT_LOOKUP[bits & 0xFF];
        }
    }
}
