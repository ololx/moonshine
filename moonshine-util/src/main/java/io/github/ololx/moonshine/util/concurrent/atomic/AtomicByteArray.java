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

import io.github.moonshine.unsafe.adapter.AtomicByteArrayAccess;
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
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 * @implNote All operations in this class are atomic, ensuring thread-safe access to the array.
 *     It uses underlying memory access provided by {@link io.github.moonshine.unsafe.adapter.AtomicByteArrayAccess}.
 * @implSpec The atomic operations provided by this class guarantee atomicity of each individual operation but do
 *     not provide atomicity of sequences of operations. Users are responsible for ensuring that sequences of operations
 *     are performed atomically when required.
 *
 *     Usage example:
 *     <pre>{@code
 *             AtomicByteArray byteArray = new AtomicByteArray(10); // Create an array with 10 elements
 *             byteArray.set(0, (byte) 42); // Set the value of an array element
 *             byte value = byteArray.get(0); // Get the value of an array element
 *             }</pre>
 */
public class AtomicByteArray {

    /**
     * Provides atomic access to the byte array elements.
     */
    private static final AtomicByteArrayAccess byteArrayAtomicAccess = new AtomicByteArrayAccess();

    /**
     * The underlying byte array.
     */
    private final byte[] array;

    /**
     * Constructs an atomic byte array of the given length.
     *
     * @param length the desired length of the atomic byte array.
     *
     * @implSpec This constructor initializes the byte array with the default value of 0 for all elements.
     */
    public AtomicByteArray(final int length) {
        array = new byte[length];
    }

    /**
     * Constructs an atomic byte array from the given byte array.
     * The input array is cloned to ensure atomicity.
     *
     * @param array the input byte array to be cloned.
     *
     * @implSpec The input array is cloned to avoid exposing the internal representation and to ensure thread
     *     safety.
     */
    public AtomicByteArray(final byte[] array) {
        this.array = array.clone();
    }

    /**
     * Gets the length of the atomic byte array.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *         int size = byteArray.length();
     *         System.out.println(size); // Prints 5
     *     }</pre>
     *
     * @return the length of the array.
     */
    public int length() {
        return array.length;
    }

    /**
     * Atomically sets the byte value at the given index to the given new value.
     *
     * @param index    the index of the element to be set.
     * @param newValue the new byte value to be stored.
     *
     * @see io.github.moonshine.unsafe.adapter.AtomicByteArrayAccess#putVolatile(byte[], int, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *             byteArray.set(3, (byte) 42);
     *             byte updatedValue = byteArray.get(3);
     *             System.out.println(updatedValue); // Prints 42
     *         }</pre>
     */
    public void set(final int index, final byte newValue) {
        byteArrayAtomicAccess.putVolatile(array, index, newValue);
    }

    /**
     * Atomically sets the byte value at the given index to the given new value and returns the old value.
     *
     * @param index    the index of the element to be set.
     * @param newValue the new byte value to be stored.
     *
     * @return the old byte value.
     *
     * @see io.github.moonshine.unsafe.adapter.AtomicByteArrayAccess#getAndSet(byte[], int, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *             byte oldValue = byteArray.getAndSet(2, (byte) 42);
     *             System.out.println(oldValue); // Prints 3
     *         }</pre>
     */
    public byte getAndSet(final int index, byte newValue) {
        return byteArrayAtomicAccess.getAndSet(array, index, newValue);
    }

    /**
     * Atomically sets the byte value at the given index to the update value if the current value equals the expected
     * value.
     *
     * @param index  the index of the element to be set.
     * @param expect the expected byte value.
     * @param update the new byte value to be stored if the current value equals the expected value.
     *
     * @return {@code true} if successful. {@code false} return indicates that the actual value was not equal to the
     *     expected value.
     *
     * @see io.github.moonshine.unsafe.adapter.AtomicByteArrayAccess#compareAndSwap(byte[], int, byte, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *             boolean wasUpdated = byteArray.compareAndSet(2, (byte) 3, (byte) 42);
     *             System.out.println(wasUpdated); // Prints true
     *         }</pre>
     */
    public boolean compareAndSet(final int index, final byte expect, final byte update) {
        return byteArrayAtomicAccess.compareAndSwap(array, index, expect, update);
    }

    /**
     * Atomically increments the byte value at the given index by one and returns the old value.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndIncrement(2);
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param index the index of the element to be incremented.
     *
     * @return the old byte value.
     */
    public final byte getAndIncrement(final int index) {
        return getAndAdd(index, (byte) 1);
    }

    /**
     * Atomically adds the given delta to the byte value at the specified index and returns the old value.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndAdd(2, (byte) 5);
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param index the index of the element to be updated.
     * @param delta the value to add to the byte at the specified index.
     *
     * @return the old byte value.
     */
    public final byte getAndAdd(final int index, byte delta) {
        return byteArrayAtomicAccess.getAndAdd(array, index, delta);
    }

    /**
     * Atomically decrements the byte value at the given index by one and returns the old value.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndDecrement(2);
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param index the index of the element to be decremented.
     *
     * @return the old byte value.
     */
    public final byte getAndDecrement(final int index) {
        return getAndAdd(index, (byte) -1);
    }

    /**
     * Atomically increments the byte value at the given index by one and returns the updated value.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.incrementAndGet(2);
     *         System.out.println(updatedValue); // Prints 31
     *     }</pre>
     *
     * @param index the index of the element to be incremented.
     *
     * @return the updated byte value.
     */
    public final byte incrementAndGet(final int index) {
        return (byte) (getAndAdd(index, (byte) 1) + 1);
    }

    /**
     * Atomically decrements the byte value at the given index by one and returns the updated value.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.decrementAndGet(2);
     *         System.out.println(updatedValue); // Prints 29
     *     }</pre>
     *
     * @param index the index of the element to be decremented.
     *
     * @return the updated byte value.
     */
    public final byte decrementAndGet(final int index) {
        return (byte) (getAndAdd(index, (byte) -1) - 1);
    }

    /**
     * Atomically adds the given delta to the byte value at the specified index and returns the updated value.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.addAndGet(2, (byte) 5);
     *         System.out.println(updatedValue); // Prints 35
     *     }</pre>
     *
     * @param index the index of the element to be updated.
     * @param delta the value to add to the byte at the specified index.
     *
     * @return the updated byte value.
     */
    public byte addAndGet(final int index, byte delta) {
        return (byte) (getAndAdd(index, delta) + delta);
    }

    /**
     * Atomically updates the byte value at the given index using the provided update function and returns the old
     * value.
     *
     * @param index          the index of the element to be updated.
     * @param updateFunction a function that computes the new byte value based on the current value.
     *
     * @return the old byte value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteUnaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *             byte oldValue = byteArray.getAndUpdate(2, val -> (byte) (val * 2));
     *             System.out.println(oldValue); // Prints 30
     *         }</pre>
     */
    public final byte getAndUpdate(final int index, ByteUnaryOperator updateFunction) {
        return byteArrayAtomicAccess.getAndUpdate(array, index, updateFunction::applyAsByte);
    }

    /**
     * Atomically updates the byte value at the given index using the provided update function and returns the updated
     * value.
     *
     * @param index          the index of the element to be updated.
     * @param updateFunction a function that computes the new byte value based on the current value.
     *
     * @return the updated byte value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteUnaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *             byte updatedValue = byteArray.updateAndGet(2, val -> (byte) (val * 2));
     *             System.out.println(updatedValue); // Prints 60
     *         }</pre>
     */
    public final byte updateAndGet(final int index, ByteUnaryOperator updateFunction) {
        return byteArrayAtomicAccess.updateAndGet(array, index, updateFunction::applyAsByte);
    }

    /**
     * Atomically updates the byte value at the specified index with the results of applying the given function to the
     * current and given values, returning the previous value.
     *
     * @param index               the index of the element to be updated.
     * @param update              the byte value to be used in the accumulator function.
     * @param accumulatorFunction a function that computes the new byte value based on the current and given values.
     *
     * @return the old byte value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteBinaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *             byte oldValue = byteArray.getAndAccumulate(2, (byte) 2, (curr, upd) -> (byte) (curr + upd));
     *             System.out.println(oldValue); // Prints 30
     *         }</pre>
     */
    public final byte getAndAccumulate(final int index, byte update, ByteBinaryOperator accumulatorFunction) {
        return byteArrayAtomicAccess.getAndAccumulate(array, index, update, accumulatorFunction::applyAsByte);
    }

    /**
     * Atomically updates the byte value at the specified index with the results of applying the given function to the
     * current and given values, returning the updated value.
     *
     * @param index               the index of the element to be updated.
     * @param update              the byte value to be used in the accumulator function.
     * @param accumulatorFunction a function that computes the new byte value based on the current and given values.
     *
     * @return the updated byte value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteBinaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *             byte updatedValue = byteArray.accumulateAndGet(2, (byte) 2, (curr, upd) -> (byte) (curr + upd));
     *             System.out.println(updatedValue); // Prints 32
     *         }</pre>
     */
    public final byte accumulateAndGet(final int index, byte update, ByteBinaryOperator accumulatorFunction) {
        return byteArrayAtomicAccess.accumulateAndGet(array, index, update, accumulatorFunction::applyAsByte);
    }

    /**
     * Returns a string representation of the current state of the array.
     *
     * <p><strong>Example usage:</strong></p>
     * <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         System.out.println(byteArray.toString()); // Prints [10, 20, 30, 40, 50]
     *     }</pre>
     *
     * @return a string representation of the array.
     */
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

    /**
     * Atomically gets the byte value at the given index.
     *
     * @param index the index of the desired element.
     *
     * @return the byte value at the given index.
     *
     * @see io.github.moonshine.unsafe.adapter.AtomicByteArrayAccess#getVolatile(byte[], int)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *             AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *             byte value = byteArray.get(2);
     *             System.out.println(value); // Prints 30
     *         }</pre>
     */
    public byte get(final int index) {
        return byteArrayAtomicAccess.getVolatile(array, index);
    }
}
