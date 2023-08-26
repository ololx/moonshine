/**
 * Copyright 2023 the project moonshine authors
 * and the original author or authors annotated by {@author}
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.ololx.moonshine.util.concurrent.atomic;

import sun.misc.Unsafe;

import java.util.function.Function;

/**
 * project moonshine
 * created 25.08.2023 16:07
 *
 * @author Alexander A. Kropotin
 */
public class AtomicByteArray {

    private static final Class<?> ARRAY_CLASS = byte[].class;

    private static final Unsafe UNSAFE = (Unsafe) UnsafeHandler.UNSAFE_INSTANCE;//Unsafe.getUnsafe();

    /*
     * The offset from the beginning of the array to the first element.
     * This value is used to provide low-level access to array elements.
     */
    private static final int ARRAY_BASE_OFFSET = UNSAFE.arrayBaseOffset(ARRAY_CLASS);

    /*
     * The bitwise shift, usual it equals 0 for byte type array (step = 1) and used like x << shift => x.
     * It is used to optimize operations on array indices. It assists in quickly calculating
     * offsets for accessing array elements without explicitly multiplying by the size of the element,
     * which can significantly improve the performance of array operations.
     */
    private static final int SHIFT;

    private final byte[] array;

    static {
        int scale = UNSAFE.arrayIndexScale(ARRAY_CLASS);
        if ((scale & (scale - 1)) != 0) {
            throw new Error("The byte[] scale is not a power of two");
        }

        SHIFT = 31 - Integer.numberOfLeadingZeros(scale);
    }

    /**
     * Creates a new AtomicByteArray of the given length, with all
     * elements initially zero.
     *
     * @param length the length of the array
     */
    public AtomicByteArray(final int length) {
        array = new byte[length];
    }

    private int checkedByteOffset(int i) {
        if (i < 0 || i >= this.array.length) {
            throw new IndexOutOfBoundsException("index " + i);
        }

        return byteOffset(i);
    }

    private static int byteOffset(int i) {
       return ARRAY_BASE_OFFSET + (i << SHIFT);
    }

    /**
     * Returns the length of the array.
     *
     * @return the length of the array
     */
    public final int length() {
        return this.array.length;
    }

    /**
     * Gets the current value at position {@code i}.
     *
     * @param i the index
     * @return the current value
     */
    public final byte get(int i) {
        return getRaw(checkedByteOffset(i));
    }

    public byte getRaw(long offset) {
        return UNSAFE.getByteVolatile(array, offset);
    }

    /**
     * Sets the element at position {@code i} to the given value.
     *
     * @param i the index
     * @param newValue the new value
     */
    public final void set(int i, byte newValue) {
        UNSAFE.putByteVolatile(array, checkedByteOffset(i), newValue);
    }

    /**
     * Atomically sets the element at position {@code i} to the given
     * updated value if the current value {@code ==} the expected value.
     *
     * @param i the index
     * @param expect the expected value
     * @param update the new value
     * @return {@code true} if successful. False return indicates that
     * the actual value was not equal to the expected value.
     */
    public final boolean compareAndSet(int i, byte expect, byte update) {
        return compareAndSetRaw(checkedByteOffset(i), expect, update);
    }

    public final boolean compareAndSet(int i, Function<Byte, Byte> binaryOperation) {
        return compareAndSetByte(this.array, checkedByteOffset(i), binaryOperation);
    }

    public boolean compareAndSetRaw(long offset, byte expect, byte update) {
        return compareAndExchangeByte(this.array, offset, expect, update) == expect;
    }

    public final byte compareAndExchangeByte(Object o, long offset,
                                             byte expected,
                                             byte x) {
        long wordOffset = offset & ~3;
        int shift = (int) (offset & 3) << 3;
        if (UnsafeHelper.IS_BIG_ENDIAN) {
            shift = 24 - shift;
        }
        int mask = 0xFF << shift;
        int maskedExpected = (expected & 0xFF) << shift;
        int maskedX = (x & 0xFF) << shift;
        int fullWord;
        int xx;
        do {
            fullWord = UNSAFE.getIntVolatile(o, wordOffset);

            if ((fullWord & mask) != maskedExpected) {
                return (byte) ((fullWord & mask) >> shift);
            }

            xx = (fullWord & ~mask) | maskedX;
        } while (!UNSAFE.compareAndSwapInt(o, wordOffset, fullWord, xx));

        return expected;
    }

    public final boolean compareAndSetByte(Object o, long offset, Function<Byte, Byte> binaryOperation) {
        long wordOffset = offset & ~3;

        int shift = (int) (offset & 3) << 3;
        if (UnsafeHelper.IS_BIG_ENDIAN) {
            shift = 24 - shift;
        }

        int mask = ~(0xFF << shift);

        int oldIntWordValue;
        int newIntWordValue;

        do {
            oldIntWordValue = UNSAFE.getIntVolatile(o, wordOffset);
            byte oldByteValue = (byte) ((oldIntWordValue >> shift) & 0xFF);
            int maskedX = (binaryOperation.apply(oldByteValue) & 0xFF) << shift;
            newIntWordValue = (oldIntWordValue & mask) | maskedX;
        } while (!UNSAFE.compareAndSwapInt(o, wordOffset, oldIntWordValue, newIntWordValue));

        return true;
    }

    /**
     * Returns the String representation of the current values of array.
     * @return the String representation of the current values of array
     */
    public String toString() {
        if (this.array.length < 1) {
            return "[]";
        }

        StringBuilder arrayStringBuilder = new StringBuilder();
        arrayStringBuilder.append('[');

        int lastElementIndex = this.array.length - 1;
        for (int elementIndex = 0; elementIndex < lastElementIndex; elementIndex++) {
            arrayStringBuilder.append(getRaw(byteOffset(elementIndex)))
                    .append(',')
                    .append(' ');
        }

        return arrayStringBuilder.append(getRaw(byteOffset(lastElementIndex)))
                .append(']')
                .toString();
    }
}
