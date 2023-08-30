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

package io.github.ololx.moonshine.util.concurrent.atomic;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 */
public class AtomicByteArray {

    private static final MemoryAccess unsafe = new MemoryAccess();

    private static final int ARRAY_BASE_OFFSET = unsafe.arrayBaseOffset(long[].class);

    private static final int ARRAY_INDEX_OFFSET_SHIFT;

    private final byte[] array;

    static {
        int indexScale = unsafe.arrayIndexScale(byte[].class);
        if ((indexScale & (indexScale - 1)) != 0) {
            throw new Error("The byte[] index scale is not a power of two");
        }

        ARRAY_INDEX_OFFSET_SHIFT = 31 - Integer.numberOfLeadingZeros(indexScale);
    }

    public AtomicByteArray(int length) {
        array = new byte[length];
    }

    public AtomicByteArray(byte[] array) {
        this.array = array.clone();
    }

    public int length() {
        return array.length;
    }

    public byte get(final int i) {
        return getRaw(checkedByteOffset(i));
    }

    private byte getRaw(long offset) {
        return unsafe.getByteVolatile(array, offset);
    }

    public void set(final int i, final byte newValue) {
        unsafe.putByteVolatile(array, checkedByteOffset(i), newValue);
    }

    public byte getAndSet(final int i, byte newValue) {
        return getAndSetRaw(checkedByteOffset(i), newValue);
    }

    private byte getAndSetRaw(long offset, byte newValue) {
        byte expected;

        do {
            expected = getRaw(offset);
        } while (!compareAndSetRaw(offset, expected, newValue));

        return expected;
    }

    public boolean compareAndSet(final int i, final byte expect, final byte update) {
        return compareAndSetRaw(checkedByteOffset(i), expect, update);
    }

    public boolean compareAndSetRaw(final long offset, final byte expect, final byte update) {
        return compareAndSwapByte(array, offset, expect, update) == update;
    }

    private byte compareAndSwapByte(Object o, long offset, byte expected, byte update) {
        long intWordOffset = offset & ~3;

        int byteWordShift = (int) (offset & 3) << 3;
        if (unsafe.isIsBigEndian()) {
            byteWordShift = 24 - byteWordShift;
        }

        int byteWordMask = 0xFF << byteWordShift;
        int maskedUpdate = (update & 0xFF) << byteWordShift;

        int oldIntWordValue;
        int newIntWordValue;

        do {
            oldIntWordValue = unsafe.getIntVolatile(o, intWordOffset);

            byte oldByteValue;
            if ((oldByteValue = (byte) ((oldIntWordValue >> byteWordShift) & 0xFF)) != expected) {
                return oldByteValue;
            }

            newIntWordValue = (oldIntWordValue & ~byteWordMask) | maskedUpdate;
        } while (!unsafe.compareAndSwapInt(o, intWordOffset, oldIntWordValue, newIntWordValue));

        return update;
    }

    public boolean weakCompareAndSet(final int i, final byte expect, final byte update) {
        return compareAndSet(i, expect, update);
    }

    public final byte getAndIncrement(int i) {
        return getAndAdd(i, (byte) 1);
    }

    /**
     * Atomically decrements by one the element at index {@code i}.
     *
     * @param i the index
     *
     * @return the previous value
     */
    public final byte getAndDecrement(int i) {
        return getAndAdd(i, (byte) -1);
    }

    public final byte getAndAdd(int i, byte delta) {
        return getAndAddRaw(checkedByteOffset(i), delta);
    }

    public byte getAndAddRaw(final long offset, final byte delta) {
        byte expected;
        byte update;

        do {
            expected = getRaw(offset);
            update = (byte) ((expected + delta) & 0xFF);
        } while (!compareAndSetRaw(offset, expected, update));

        return expected;
    }

    public final byte incrementAndGet(int i) {
        return (byte) (getAndAdd(i, (byte) 1) + 1);
    }

    public final byte decrementAndGet(int i) {
        return (byte) (getAndAdd(i, (byte) -1) - 1);
    }

    public byte addAndGet(int i, byte delta) {
        return (byte) (getAndAdd(i, delta) + delta);
    }

    public final int getAndUpdate(int i, UnaryOperator<Byte> updateFunction) {
        long offset = checkedByteOffset(i);

        byte prev;
        byte next;

        do {
            prev = getRaw(offset);
            next = updateFunction.apply(prev);
        } while (!compareAndSetRaw(offset, prev, next));

        return prev;
    }

    public final int updateAndGet(int i, UnaryOperator<Byte> updateFunction) {
        long offset = checkedByteOffset(i);

        byte prev;
        byte next;

        do {
            prev = getRaw(offset);
            next = updateFunction.apply(prev);
        } while (!compareAndSetRaw(offset, prev, next));

        return next;
    }

    public final int getAndAccumulate(int i, byte update, BinaryOperator<Byte> accumulatorFunction) {
        long offset = checkedByteOffset(i);

        byte prev;
        byte next;

        do {
            prev = getRaw(offset);
            next = accumulatorFunction.apply(prev, update);
        } while (!compareAndSetRaw(offset, prev, next));

        return prev;
    }

    public final int accumulateAndGet(int i, byte update, BinaryOperator<Byte> accumulatorFunction) {
        long offset = checkedByteOffset(i);

        byte prev;
        byte next;

        do {
            prev = getRaw(offset);
            next = accumulatorFunction.apply(prev, update);
        } while (!compareAndSetRaw(offset, prev, next));

        return next;
    }

    private int checkedByteOffset(int i) {
        if (i < 0 || i >= array.length) {throw new IndexOutOfBoundsException("index " + i);}

        return byteOffset(i);
    }

    private static int byteOffset(int i) {
        return (i << ARRAY_INDEX_OFFSET_SHIFT) + ARRAY_BASE_OFFSET;
    }

    @Override
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
