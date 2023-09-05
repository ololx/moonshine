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
 * This class represents an atomic byte array, providing safe concurrent access to its elements.
 * It offers atomic operations for reading and writing byte array elements, such as get, set, getAndSet,
 * compareAndSet, as well as increment, decrement, and arithmetic operations on array elements.
 *
 * You can create an AtomicByteArray instance using constructors that take either the array's size or
 * an existing byte array.
 *
 * @implNote All operations in this class are atomic, ensuring thread-safe access to the array.
 *     It uses underlying memory access provided by {@link AtomicAccess}.
 * @implSpec The atomic operations provided by this class guarantee atomicity of each individual operation but do
 *     not provide atomicity of sequences of operations. Users are responsible for ensuring that sequences of operations
 *     are performed atomically when required.
 *
 *     Usage example:
 *     <pre>{@code
 *     AtomicByteArray byteArray = new AtomicByteArray(10); // Create an array with 10 elements
 *     byteArray.set(0, (byte) 42); // Set the value of an array element
 *     byte value = byteArray.get(0); // Get the value of an array element
 *     }</pre>
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 */
public class AtomicByteArray {

    private static final AtomicAccess.ByteArray byteArrayAtomicAccess = new AtomicAccess.ByteArray();

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
}
