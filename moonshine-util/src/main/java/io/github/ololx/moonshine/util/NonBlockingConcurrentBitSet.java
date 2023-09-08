package io.github.ololx.moonshine.util;

import io.github.ololx.moonshine.util.concurrent.atomic.AtomicByteArray;

import java.util.function.IntUnaryOperator;

/**
 * A thread-safe implementation of a concurrent bitset using atomic operations.
 *
 * @author Alexander A. Kropotin
 * @apiNote This class provides methods to manipulate individual bits in a thread-safe manner.
 *     It is designed for scenarios where multiple threads need to access and modify
 *     a shared bitset concurrently.
 *
 *     project moonshine
 *     created 01.08.2023 10:52
 */
public class NonBlockingConcurrentBitSet implements ConcurrentBitSet {

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
    private final int maxIndex;

    /**
     * Creates a new NonBlockingConcurrentBitset with the specified size.
     *
     * @param size The number of bits in the bitset.
     */
    public NonBlockingConcurrentBitSet(int size) {
        this.data = new AtomicByteArray((size + WORD_SIZE - 1) / WORD_SIZE);
        this.maxIndex = size - 1;
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
        int bitMask = NonBlockingConcurrentBitSet.bitMaskOperator.applyAsInt(bitOffset);
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
        int bitMask = 1 << bitIndexShiftOperator.applyAsInt(bitIndex);
        this.data.updateAndGet(wordIndexOperator.applyAsInt(bitIndex), word -> (byte) (word | bitMask));
    }

    /**
     * Clears the bit at the specified index (sets it to 0).
     *
     * @param bitIndex The index of the bit to clear.
     */
    @Override
    public void clear(int bitIndex) {
        checkIndex(bitIndex);
        int bitMask = 1 << bitIndexShiftOperator.applyAsInt(bitIndex);
        this.data.updateAndGet(wordIndexOperator.applyAsInt(bitIndex), word -> (byte) (word & ~bitMask));
    }

    /**
     * Flips the value of the bit at the specified index (0 becomes 1, and 1 becomes 0).
     *
     * @param bitIndex The index of the bit to flip.
     */
    @Override
    public void flip(int bitIndex) {
        checkIndex(bitIndex);
        int bitMask = 1 << bitIndexShiftOperator.applyAsInt(bitIndex);
        this.data.updateAndGet(wordIndexOperator.applyAsInt(bitIndex), word -> (byte) (word ^ bitMask));
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
        if (bitIndex < 0 || bitIndex > maxIndex) {
            throw new IndexOutOfBoundsException(String.format(
                "The bitIndex %s out of bounds [%s, %s]", bitIndex, 0, maxIndex
            ));
        }
    }
}
