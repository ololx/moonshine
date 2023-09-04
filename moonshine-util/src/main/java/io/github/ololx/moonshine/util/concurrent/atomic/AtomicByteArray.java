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

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 */
public class AtomicByteArray {

    private static final ByteArrayAtomicAccess byteArrayAtomicAccess = new ByteArrayAtomicAccess();

    private final byte[] array;

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
        return byteArrayAtomicAccess.getVolatile(array, i);
    }

    public void set(final int i, final byte newValue) {
        byteArrayAtomicAccess.putVolatile(array, i, newValue);
    }

    public byte getAndSet(final int i, byte newValue) {
        return byteArrayAtomicAccess.getAndSet(array, i, newValue);
    }

    public boolean compareAndSet(final int i, final byte expect, final byte update) {
        return byteArrayAtomicAccess.compareAndSwap(array, i, expect, update);
    }

    public final byte getAndIncrement(int i) {
        return getAndAdd(i, (byte) 1);
    }

    public final byte getAndDecrement(int i) {
        return getAndAdd(i, (byte) -1);
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

    public final byte getAndAdd(int i, byte delta) {
        return byteArrayAtomicAccess.getAndAdd(array, i, delta);
    }

    public final byte getAndUpdate(int i, ByteUnaryOperator updateFunction) {
        return byteArrayAtomicAccess.getAndUpdate(array, i, updateFunction);
    }

    public final byte updateAndGet(int i, ByteUnaryOperator updateFunction) {
        return byteArrayAtomicAccess.updateAndGet(array, i, updateFunction);
    }

    public final byte getAndAccumulate(int i, byte update, ByteBinaryOperator accumulatorFunction) {
        return byteArrayAtomicAccess.getAndAccumulate(array, i, update, accumulatorFunction);
    }

    public final byte accumulateAndGet(int i, byte update, ByteBinaryOperator accumulatorFunction) {
        return byteArrayAtomicAccess.accumulateAndGet(array, i, update, accumulatorFunction);
    }

    @Override
    public String toString() {
        int lastElementIndex = array.length - 1;

        if (lastElementIndex < 0) {
            return "[]";
        }

        // initial capacity = lastElementIndex * (4 (symbols max for byte) + 2 (symbols for ', ')
        StringBuilder arrayStringBuilder = new StringBuilder(lastElementIndex * 6);
        arrayStringBuilder.append('[');

        for (int elementIndex = 0; ; elementIndex++) {
            arrayStringBuilder.append(this.get(elementIndex));

            if (elementIndex == lastElementIndex) {
                break;
            }

            arrayStringBuilder.append(", ");
        }

        return arrayStringBuilder.append(']')
            .toString();
    }

    static class ByteArrayAtomicAccess {

        private static final Class<?> ARRAY_CLASS = byte[].class;

        private static final int ARRAY_BASE_OFFSET;

        private static final long ARRAY_INDEX_OFFSET_SHIFT;

        private static final MemoryAtomicAccess memoryAccess = new MemoryAtomicAccess();

        static {
            ARRAY_BASE_OFFSET = memoryAccess.arrayBaseOffset(ARRAY_CLASS);

            int indexScale = memoryAccess.arrayIndexScale(ARRAY_CLASS);
            if ((indexScale & (indexScale - 1)) != 0) {
                throw new Error("The byte[] index scale is not a power of two");
            }

            ARRAY_INDEX_OFFSET_SHIFT = 31 - Integer.numberOfLeadingZeros(indexScale);
        }

        private ByteArrayAtomicAccess() {}

        private static long checkedByteOffset(final byte[] array, final int i) {
            if (i < 0 || i >= array.length) {
                throw new IndexOutOfBoundsException(String.format(
                    "The index %s out of a bounds [%s, %s]", i, 0, array.length - 1
                ));
            }

            return ((long) i << ARRAY_INDEX_OFFSET_SHIFT) + ARRAY_BASE_OFFSET;
        }

        public byte getVolatile(final byte[] array, final int i) {
            return memoryAccess.getByteVolatile(array, checkedByteOffset(array, i));
        }

        public void putVolatile(final byte[] array, final int i, final byte newValue) {
            memoryAccess.putByteVolatile(array, checkedByteOffset(array, i), newValue);
        }

        public byte getAndSet(final byte[] array, final int i, final byte newValue) {
            return memoryAccess.getAndSetByte(array, checkedByteOffset(array, i), newValue);
        }

        public boolean compareAndSwap(final byte[] array, final int i, final byte expect, final byte update) {
            return memoryAccess.compareAndSwapByte(array, checkedByteOffset(array, i), expect, update);
        }

        public byte getAndAdd(final byte[] array, final int i, final byte delta) {
            return memoryAccess.getAndAddByte(array, checkedByteOffset(array, i), delta);
        }

        public byte getAndUpdate(final byte[] array, final int i, final ByteUnaryOperator updateFunction) {
            return memoryAccess.getAndUpdateByte(array, checkedByteOffset(array, i), updateFunction);
        }

        public byte updateAndGet(final byte[] array, final int i, final ByteUnaryOperator updateFunction) {
            return memoryAccess.updateAndGetByte(array, checkedByteOffset(array, i), updateFunction);
        }

        public byte getAndAccumulate(final byte[] array,
                                     final int i,
                                     final byte update,
                                     final ByteBinaryOperator accumulatorFunction) {
            return memoryAccess.getAndAccumulateByte(array, checkedByteOffset(array, i), update, accumulatorFunction);
        }

        public byte accumulateAndGet(final byte[] array,
                                     final int i,
                                     final byte update,
                                     final ByteBinaryOperator accumulatorFunction) {
            return memoryAccess.accumulateAndGetByte(array, checkedByteOffset(array, i), update, accumulatorFunction);
        }
    }
}
