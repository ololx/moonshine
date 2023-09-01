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

import io.github.ololx.moonshine.util.function.ByteBinaryOperator;
import io.github.ololx.moonshine.util.function.ByteUnaryOperator;

import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 */
public class AtomicByteArray {

    private static final MemoryAccess memoryAccess = new MemoryAccess();

    private static final int ARRAY_BASE_OFFSET = memoryAccess.arrayBaseOffset(long[].class);

    private static final int ARRAY_INDEX_OFFSET_SHIFT;

    private final byte[] array;

    static {
        int indexScale = memoryAccess.arrayIndexScale(byte[].class);
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
        return memoryAccess.getByteVolatile(array, offset);
    }

    public void set(final int i, final byte newValue) {
        memoryAccess.putByteVolatile(array, checkedByteOffset(i), newValue);
    }

    public byte getAndSet(final int i, byte newValue) {
        return getAndSetRaw(checkedByteOffset(i), newValue);
    }

    private byte getAndSetRaw(long offset, byte newValue) {
       return memoryAccess.getAndSetByte(array, offset, newValue);
    }

    public boolean compareAndSet(final int i, final byte expect, final byte update) {
        return compareAndSetRaw(checkedByteOffset(i), expect, update);
    }

    public boolean compareAndSetRaw(final long offset, final byte expect, final byte update) {
        return memoryAccess.compareAndSwapByte(array, offset, expect, update);
    }

    public boolean weakCompareAndSet(final int i, final byte expect, final byte update) {
        return compareAndSet(i, expect, update);
    }

    public final byte getAndIncrement(int i) {
        return getAndAdd(i, (byte) 1);
    }

    public final byte getAndDecrement(int i) {
        return getAndAdd(i, (byte) -1);
    }

    public final byte getAndAdd(int i, byte delta) {
        return getAndAddRaw(checkedByteOffset(i), delta);
    }

    public byte getAndAddRaw(final long offset, final byte delta) {
        return memoryAccess.getAndAddByte(array, offset, delta);
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

    public final int getAndUpdate(int i, ByteUnaryOperator updateFunction) {
        return memoryAccess.getAndUpdateByte(array, checkedByteOffset(i), updateFunction);
    }

    public final int updateAndGet(int i, ByteUnaryOperator updateFunction) {
        return memoryAccess.updateAndGetByte(array, checkedByteOffset(i), updateFunction);
    }

    public final int getAndAccumulate(int i, byte update, ByteBinaryOperator accumulatorFunction) {
        return memoryAccess.getAndAccumulateByte(array, checkedByteOffset(i), update, accumulatorFunction);
    }

    public final int accumulateAndGet(int i, byte update, ByteBinaryOperator accumulatorFunction) {
        return memoryAccess.accumulateAndGetByte(array, checkedByteOffset(i), update, accumulatorFunction);
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
