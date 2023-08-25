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

package io.github.ololx.moonshine.util;

/**
 * project moonshine
 * created 25.08.2023 16:07
 *
 * @author Alexander A. Kropotin
 */
public class AtomicByteArray {

    private static final UnsafeHelper UNSAFE_HELPER = UnsafeHelper.getInstance();

    /*
     * The offset from the beginning of the array to the first element.
     * This value is used to provide low-level access to array elements.
     */
    private static final int ARRAY_BASE_OFFSET = UNSAFE_HELPER.arrayBaseOffset(byte[].class);

    /*
     * The bitwise shift, usual it equals 0 for byte type array (step = 1) and used like x << shift => x.
     * It is used to optimize operations on array indices. It assists in quickly calculating
     * offsets for accessing array elements without explicitly multiplying by the size of the element,
     * which can significantly improve the performance of array operations.
     */
    private static final int SHIFT;

    private final byte[] array;

    static {
        int scale = UNSAFE_HELPER.arrayIndexScale(byte[].class);
        if ((scale & (scale - 1)) != 0)
            throw new Error("data type scale not a power of two");
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

    /**
     * Creates a new AtomicByteArray with the same length as, and
     * all elements copied from, the given array.
     *
     * @param array the array to copy elements from
     * @throws NullPointerException if array is null
     */
    public AtomicByteArray(final byte[] array) {
        this.array = array.clone();
    }

    private int checkedByteOffset(int i) {
        if (i < 0 || i >= this.array.length)
            throw new IndexOutOfBoundsException("index " + i);

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
    public final long get(int i) {
        return getRaw(checkedByteOffset(i));
    }

    private long getRaw(long offset) {
        return UNSAFE_HELPER.getByteVolatile(array, offset);
    }

    /**
     * Sets the element at position {@code i} to the given value.
     *
     * @param i the index
     * @param newValue the new value
     */
    public final void set(int i, byte newValue) {
        UNSAFE_HELPER.putByteVolatile(array, checkedByteOffset(i), newValue);
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

    private boolean compareAndSetRaw(long offset, byte expect, byte update) {
        return UNSAFE_HELPER.compareAndSwapByte(array, offset, expect, update);
    }

    /**
     * Returns the String representation of the current values of array.
     * @return the String representation of the current values of array
     */
    public String toString() {
        int iMax = array.length - 1;
        if (iMax == -1)
            return "[]";

        StringBuilder b = new StringBuilder();
        b.append('[');
        for (int i = 0; ; i++) {
            b.append(getRaw(byteOffset(i)));
            if (i == iMax)
                return b.append(']').toString();
            b.append(',').append(' ');
        }
    }
}
