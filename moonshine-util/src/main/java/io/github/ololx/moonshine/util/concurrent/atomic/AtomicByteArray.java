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
 *     It uses underlying memory access provided by {@link AtomicAccess.ByteArray}.
 * @implSpec The atomic operations provided by this class guarantee atomicity of each individual operation but do
 *     not provide atomicity of sequences of operations. Users are responsible for ensuring that sequences of operations
 *     are performed atomically when required.
 *
 *     Usage example:
 *     <pre>{@code
 *         AtomicByteArray byteArray = new AtomicByteArray(10); // Create an array with 10 elements
 *         byteArray.set(0, (byte) 42); // Set the value of an array element
 *         byte value = byteArray.get(0); // Get the value of an array element
 *         }</pre>
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 */
public class AtomicByteArray {

    /**
     * Provides atomic access to the byte array elements.
     */
    private static final AtomicAccess.ByteArray byteArrayAtomicAccess = new AtomicAccess.ByteArray();

    /**
     * The underlying byte array.
     */
    private final byte[] array;

    /**
     * Constructs an atomic byte array of the given length.
     *
     * @implSpec This constructor initializes the byte array with the default value of 0 for all elements.
     *
     * @param length the desired length of the atomic byte array.
     */
    public AtomicByteArray(final int length) {
        array = new byte[length];
    }

    /**
     * Constructs an atomic byte array from the given byte array.
     * The input array is cloned to ensure atomicity.
     *
     * @implSpec The input array is cloned to avoid exposing the internal representation and to ensure thread
     *     safety.
     *
     * @param array the input byte array to be cloned.
     */
    public AtomicByteArray(final byte[] array) {
        this.array = array.clone();
    }

    /**
     * Gets the length of the atomic byte array.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
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
     * Atomically gets the byte value at the given index.
     *
     * @see AtomicAccess.ByteArray#getVolatile(byte[], int)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte value = byteArray.get(2);
     *         System.out.println(value); // Prints 30
     *     }</pre>
     *
     * @param i the index of the desired element.
     *
     * @return the byte value at the given index.
     */
    public byte get(final int i) {
        return byteArrayAtomicAccess.getVolatile(array, i);
    }

    /**
     * Atomically sets the byte value at the given index to the given new value.
     *
     * @see AtomicAccess.ByteArray#putVolatile(byte[], int, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *         byteArray.set(3, (byte) 42);
     *         byte updatedValue = byteArray.get(3);
     *         System.out.println(updatedValue); // Prints 42
     *     }</pre>
     *
     * @param i        the index of the element to be set.
     * @param newValue the new byte value to be stored.
     */
    public void set(final int i, final byte newValue) {
        byteArrayAtomicAccess.putVolatile(array, i, newValue);
    }

    /**
     * Atomically sets the byte value at the given index to the given new value and returns the old value.
     *
     * @see AtomicAccess.ByteArray#getAndSet(byte[], int, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *         byte oldValue = byteArray.getAndSet(2, (byte) 42);
     *         System.out.println(oldValue); // Prints 3
     *     }</pre>
     *
     * @param i        the index of the element to be set.
     * @param newValue the new byte value to be stored.
     *
     * @return the old byte value.
     */
    public byte getAndSet(final int i, byte newValue) {
        return byteArrayAtomicAccess.getAndSet(array, i, newValue);
    }

    /**
     * Atomically sets the byte value at the given index to the update value if the current value equals the expected
     * value.
     *
     * @see AtomicAccess.ByteArray#compareAndSwap(byte[], int, byte, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{1, 2, 3, 4, 5});
     *         boolean wasUpdated = byteArray.compareAndSet(2, (byte) 3, (byte) 42);
     *         System.out.println(wasUpdated); // Prints true
     *     }</pre>
     *
     * @param i      the index of the element to be set.
     * @param expect the expected byte value.
     * @param update the new byte value to be stored if the current value equals the expected value.
     *
     * @return {@code true} if successful. {@code false} return indicates that the actual value was not equal to the
     *     expected value.
     */
    public boolean compareAndSet(final int i, final byte expect, final byte update) {
        return byteArrayAtomicAccess.compareAndSwap(array, i, expect, update);
    }

    /**
     * Atomically increments the byte value at the given index by one and returns the old value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndIncrement(2);
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param i the index of the element to be incremented.
     *
     * @return the old byte value.
     */
    public final byte getAndIncrement(int i) {
        return getAndAdd(i, (byte) 1);
    }

    /**
     * Atomically decrements the byte value at the given index by one and returns the old value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndDecrement(2);
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param i the index of the element to be decremented.
     *
     * @return the old byte value.
     */
    public final byte getAndDecrement(int i) {
        return getAndAdd(i, (byte) -1);
    }

    /**
     * Atomically increments the byte value at the given index by one and returns the updated value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.incrementAndGet(2);
     *         System.out.println(updatedValue); // Prints 31
     *     }</pre>
     *
     * @param i the index of the element to be incremented.
     *
     * @return the updated byte value.
     */
    public final byte incrementAndGet(int i) {
        return (byte) (getAndAdd(i, (byte) 1) + 1);
    }

    /**
     * Atomically decrements the byte value at the given index by one and returns the updated value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.decrementAndGet(2);
     *         System.out.println(updatedValue); // Prints 29
     *     }</pre>
     *
     * @param i the index of the element to be decremented.
     *
     * @return the updated byte value.
     */
    public final byte decrementAndGet(int i) {
        return (byte) (getAndAdd(i, (byte) -1) - 1);
    }

    /**
     * Atomically adds the given delta to the byte value at the specified index and returns the updated value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.addAndGet(2, (byte) 5);
     *         System.out.println(updatedValue); // Prints 35
     *     }</pre>
     *
     * @param i     the index of the element to be updated.
     * @param delta the value to add to the byte at the specified index.
     *
     * @return the updated byte value.
     */
    public byte addAndGet(int i, byte delta) {
        return (byte) (getAndAdd(i, delta) + delta);
    }

    /**
     * Atomically adds the given delta to the byte value at the specified index and returns the old value.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndAdd(2, (byte) 5);
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param i     the index of the element to be updated.
     * @param delta the value to add to the byte at the specified index.
     *
     * @return the old byte value.
     */
    public final byte getAndAdd(int i, byte delta) {
        return byteArrayAtomicAccess.getAndAdd(array, i, delta);
    }

    /**
     * Atomically updates the byte value at the given index using the provided update function and returns the old
     * value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteUnaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndUpdate(2, val -> (byte) (val * 2));
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param i              the index of the element to be updated.
     * @param updateFunction a function that computes the new byte value based on the current value.
     *
     * @return the old byte value.
     */
    public final byte getAndUpdate(int i, ByteUnaryOperator updateFunction) {
        return byteArrayAtomicAccess.getAndUpdate(array, i, updateFunction);
    }

    /**
     * Atomically updates the byte value at the given index using the provided update function and returns the updated
     * value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteUnaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.updateAndGet(2, val -> (byte) (val * 2));
     *         System.out.println(updatedValue); // Prints 60
     *     }</pre>
     *
     * @param i              the index of the element to be updated.
     * @param updateFunction a function that computes the new byte value based on the current value.
     *
     * @return the updated byte value.
     */
    public final byte updateAndGet(int i, ByteUnaryOperator updateFunction) {
        return byteArrayAtomicAccess.updateAndGet(array, i, updateFunction);
    }

    /**
     * Atomically updates the byte value at the specified index with the results of applying the given function to the
     * current and given values, returning the previous value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteBinaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte oldValue = byteArray.getAndAccumulate(2, (byte) 2, (curr, upd) -> (byte) (curr + upd));
     *         System.out.println(oldValue); // Prints 30
     *     }</pre>
     *
     * @param i                   the index of the element to be updated.
     * @param update              the byte value to be used in the accumulator function.
     * @param accumulatorFunction a function that computes the new byte value based on the current and given values.
     *
     * @return the old byte value.
     */
    public final byte getAndAccumulate(int i, byte update, ByteBinaryOperator accumulatorFunction) {
        return byteArrayAtomicAccess.getAndAccumulate(array, i, update, accumulatorFunction);
    }

    /**
     * Atomically updates the byte value at the specified index with the results of applying the given function to the
     * current and given values, returning the updated value.
     *
     * @see io.github.ololx.moonshine.util.function.ByteBinaryOperator
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicByteArray byteArray = new AtomicByteArray(new byte[]{10, 20, 30, 40, 50});
     *         byte updatedValue = byteArray.accumulateAndGet(2, (byte) 2, (curr, upd) -> (byte) (curr + upd));
     *         System.out.println(updatedValue); // Prints 32
     *     }</pre>
     *
     * @param i                   the index of the element to be updated.
     * @param update              the byte value to be used in the accumulator function.
     * @param accumulatorFunction a function that computes the new byte value based on the current and given values.
     *
     * @return the updated byte value.
     */
    public final byte accumulateAndGet(int i, byte update, ByteBinaryOperator accumulatorFunction) {
        return byteArrayAtomicAccess.accumulateAndGet(array, i, update, accumulatorFunction);
    }

    /**
     * Returns a string representation of the current state of the array.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
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
}
