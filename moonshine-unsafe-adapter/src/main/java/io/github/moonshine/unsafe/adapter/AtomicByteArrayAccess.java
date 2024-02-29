/**
 * Copyright 2024 the project moonshine authors
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

package io.github.moonshine.unsafe.adapter;

import io.github.moonshine.unsafe.adapter.functional.ByteBinaryAccumulator;
import io.github.moonshine.unsafe.adapter.functional.ByteUnaryAccumulator;

/**
 * A class to provide atomic operations on byte arrays.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 28/02/2024 10:35 am
 * @implSpec This class is designed for atomic operations on byte arrays using underlying
 *     atomic memory access semantics provided by {@link io.github.moonshine.unsafe.adapter.AtomicAccess}. The
 *     operations include volatile gets/sets,
 *     atomic compare-and-swap, get-and-add, get-and-update, and accumulate operations.
 * @see io.github.moonshine.unsafe.adapter.AtomicAccess
 */
public final class AtomicByteArrayAccess implements AtomicVarAccess {

    /**
     * Represents the class of a byte array.
     */
    private static final Class<?> ARRAY_CLASS = byte[].class;

    /**
     * The offset of the base address of the byte array.
     */
    private static final int ARRAY_BASE_OFFSET;

    /**
     * The offset shift for indexing into the byte array.
     */
    private static final int ARRAY_INDEX_OFFSET_SHIFT;

    /**
     * Instance of {@code AtomicAccess} for atomic operations.
     */
    private static final AtomicAccess atomicAccess = new AtomicAccess();

    /**
     * Static initialization block for setting up class-level constants related to direct memory access.
     */
    static {
        // Get the base offset of byte arrays for direct memory access.
        ARRAY_BASE_OFFSET = atomicAccess.arrayBaseOffset(ARRAY_CLASS);

        // Determine and Verify the index scale (the number of bytes between each element of the array) of byte
        // arrays.
        // In most JVM implementations, it should be as arrays are typically allocated in contiguous memory blocks.
        int indexScale = atomicAccess.arrayIndexScale(ARRAY_CLASS);
        if ((indexScale & (indexScale - 1)) != 0) {
            throw new Error("The byte[] index scale is not a power of two");
        }

        // Compute the shift value based on the index scale.
        // This shift is useful when working with atomic operations that require element-wise offsets.
        ARRAY_INDEX_OFFSET_SHIFT = 31 - Integer.numberOfLeadingZeros(indexScale);
    }

    /**
     * Returns the byte value at the given index of the byte array using volatile semantics.
     *
     * @param array The byte array to fetch the value from.
     * @param index The index of the element to fetch.
     *
     * @return The byte value at the given index.
     *
     * @implSpec This method leverages {@link io.github.moonshine.unsafe.adapter.AtomicAccess#getByteVolatile(Object, long)}
     *     to achieve volatile memory access semantics.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#getByteVolatile(Object, long)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *                 AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *                 byte[] array = {1, 2, 3, 4, 5};
     *                 byte value = byteArray.getVolatile(array, 2);
     *                 System.out.println(value); // Prints 3
     *                 }</pre>
     */
    public byte getVolatile(final byte[] array, final int index) {
        return atomicAccess.getByteVolatile(array, checkedByteOffset(array, index));
    }

    /**
     * Validates the index against the byte array bounds and calculates the byte offset.
     *
     * @param array The byte array to be checked.
     * @param index The index to be checked.
     *
     * @return The calculated byte offset.
     *
     * @throws IndexOutOfBoundsException if the index is out of bounds.
     * @implSpec This method calculates the offset for the given index in the byte array
     *     based on the internal configuration set during class initialization.
     */
    private static long checkedByteOffset(final byte[] array, final int index) {
        if (index < 0 || index >= array.length) {
            throw new IndexOutOfBoundsException(String.format(
                "The index %s out of a bounds [%s, %s]", index, 0, array.length - 1
            ));
        }

        return ((long) index << ARRAY_INDEX_OFFSET_SHIFT) + ARRAY_BASE_OFFSET;
    }

    /**
     * Sets the byte value at the given index of the byte array using volatile semantics.
     *
     * @param array    The byte array to update.
     * @param index    The index of the element to update.
     * @param newValue The new byte value to set.
     *
     * @implSpec This method uses {@link io.github.moonshine.unsafe.adapter.AtomicAccess#putByteVolatile(Object, long, byte)}
     *     to provide volatile memory semantics.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#putByteVolatile(Object, long, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byteArray.putVolatile(array, 2, (byte) 8);
     *         }</pre>
     */
    public void putVolatile(final byte[] array, final int index, final byte newValue) {
        atomicAccess.putByteVolatile(array, checkedByteOffset(array, index), newValue);
    }

    /**
     * Atomically sets the element at position {@code index} to the given {@code newValue}
     * and returns the old value.
     *
     * @param array    The byte array to update.
     * @param index    The index of the element to update.
     * @param newValue The new byte value to set.
     *
     * @return The previous value at the given index.
     *
     * @implSpec This method uses {@link io.github.moonshine.unsafe.adapter.AtomicAccess#getAndSetByte(Object, long, byte)}
     *     for atomic updates.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#getAndSetByte(Object, long, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byte oldValue = byteArray.getAndSet(array, 2, (byte) 8);
     *         System.out.println(oldValue); // Prints 3
     *         }</pre>
     */
    public byte getAndSet(final byte[] array, final int index, final byte newValue) {
        return atomicAccess.getAndSetByte(array, checkedByteOffset(array, index), newValue);
    }

    /**
     * Atomically updates the byte at the given index with the expected value.
     *
     * @param array  The byte array to update.
     * @param index  The index of the element to update.
     * @param expect The expected byte value.
     * @param update The new byte value to set if the current value is {@code expect}.
     *
     * @return {@code true} if successful; or {@code false} if the actual value
     *     was not equal to the expected value.
     *
     * @implSpec This method uses {@link io.github.moonshine.unsafe.adapter.AtomicAccess#compareAndSwapByte(Object, long, byte, byte)}
     *     for atomic comparison and swap.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#compareAndSwapByte(Object, long, byte, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         boolean swapped = byteArray.compareAndSwap(array, 2, (byte) 3, (byte) 8);
     *         System.out.println(swapped); // Prints true
     *         }</pre>
     */
    public boolean compareAndSwap(final byte[] array, final int index, final byte expect, final byte update) {
        return atomicAccess.compareAndSwapByte(array, checkedByteOffset(array, index), expect, update);
    }

    /**
     * Atomically adds the given value to the element at the specified index.
     *
     * @param array The byte array to update.
     * @param index The index of the element to update.
     * @param delta The value to add.
     *
     * @return The previous value at the given index.
     *
     * @implSpec This method uses {@link io.github.moonshine.unsafe.adapter.AtomicAccess#getAndAddByte(Object, long, byte)}
     *     for atomic addition.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#getAndAddByte(Object, long, byte)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byte oldValue = byteArray.getAndAdd(array, 2, (byte) 2);
     *         System.out.println(oldValue); // Prints 3
     *         }</pre>
     */
    public byte getAndAdd(final byte[] array, final int index, final byte delta) {
        return atomicAccess.getAndAddByte(array, checkedByteOffset(array, index), delta);
    }

    /**
     * Atomically updates the byte at the given index using the provided update function.
     *
     * @param array          The byte array to update.
     * @param index          The index of the element to update.
     * @param updateFunction A unary operator function to apply.
     *
     * @return The previous value at the given index.
     *
     * @implSpec This method uses {@link io.github.moonshine.unsafe.adapter.AtomicAccess#getAndUpdateByte(Object, long, io.github.moonshine.unsafe.adapter.functional.ByteUnaryAccumulator)}
     *     for atomic updates.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#getAndUpdateByte(Object, long, io.github.moonshine.unsafe.adapter.functional.ByteUnaryAccumulator)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byte oldValue = byteArray.getAndUpdate(array, 2, val -> (byte) (val + 1));
     *         System.out.println(oldValue); // Prints 3
     *         }</pre>
     */
    public byte getAndUpdate(final byte[] array, final int index, final ByteUnaryAccumulator updateFunction) {
        return atomicAccess.getAndUpdateByte(array, checkedByteOffset(array, index), updateFunction);
    }

    /**
     * Atomically updates the byte at the given index using the provided update function,
     * and then returns the updated value.
     *
     * @param array          The byte array to update.
     * @param index          The index of the element to update.
     * @param updateFunction A unary operator function to apply.
     *
     * @return The updated value at the given index.
     *
     * @implSpec This method uses {@link io.github.moonshine.unsafe.adapter.AtomicAccess#updateAndGetByte(Object, long, io.github.moonshine.unsafe.adapter.functional.ByteUnaryAccumulator)}
     *     for atomic updates.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#updateAndGetByte(Object, long, io.github.moonshine.unsafe.adapter.functional.ByteUnaryAccumulator)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byte updatedValue = byteArray.updateAndGet(array, 2, val -> (byte) (val + 1));
     *         System.out.println(updatedValue); // Prints 4
     *         }</pre>
     */
    public byte updateAndGet(final byte[] array, final int index, final ByteUnaryAccumulator updateFunction) {
        return atomicAccess.updateAndGetByte(array, checkedByteOffset(array, index), updateFunction);
    }

    /**
     * Atomically updates the byte at the given index using the accumulator function and given update.
     *
     * @param array               The byte array to update.
     * @param index               The index of the element to update.
     * @param update              The byte value to use with the accumulator function.
     * @param accumulatorFunction A binary operator function to apply.
     *
     * @return The previous value at the given index.
     *
     * @implSpec This method uses
     *     {@link io.github.moonshine.unsafe.adapter.AtomicAccess#getAndAccumulateByte(Object, long, byte, io.github.moonshine.unsafe.adapter.functional.ByteBinaryAccumulator)}
     *     for atomic accumulation.
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#getAndAccumulateByte(Object, long, byte, io.github.moonshine.unsafe.adapter.functional.ByteBinaryAccumulator)
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byte oldValue = byteArray.getAndAccumulate(array, 2, (byte) 2, (a, b) -> (byte) (a + b));
     *         System.out.println(oldValue); // Prints 3
     *         }</pre>
     */
    public byte getAndAccumulate(final byte[] array,
                                 final int index,
                                 final byte update,
                                 final ByteBinaryAccumulator accumulatorFunction) {
        return atomicAccess.getAndAccumulateByte(
            array,
            checkedByteOffset(array, index),
            update,
            accumulatorFunction
        );
    }

    /**
     * Atomically updates the byte at the given index using the accumulator function and given update,
     * and then returns the updated value.
     *
     * @param array               The byte array to update.
     * @param index               The index of the element to update.
     * @param update              The byte value to use with the accumulator function.
     * @param accumulatorFunction A binary operator function to apply.
     *
     * @return The updated value at the given index.
     *
     * @implSpec This method uses
     *     {@link io.github.moonshine.unsafe.adapter.AtomicAccess#accumulateAndGetByte(Object, long, byte, io.github.moonshine.unsafe.adapter.functional.ByteBinaryAccumulator)}
     *     for atomic accumulation.
     *
     *     <p><strong>Example usage:</strong></p>
     *     <pre>{@code
     *         AtomicAccess.ByteArray byteArray = new AtomicAccess.ByteArray();
     *         byte[] array = {1, 2, 3, 4, 5};
     *         byte updatedValue = byteArray.accumulateAndGet(array, 2, (byte) 2, (a, b) -> (byte) (a + b));
     *         System.out.println(updatedValue); // Prints 5
     *         }</pre>
     * @see io.github.moonshine.unsafe.adapter.AtomicAccess#accumulateAndGetByte(Object, long, byte, io.github.moonshine.unsafe.adapter.functional.ByteBinaryAccumulator)
     */
    public byte accumulateAndGet(final byte[] array,
                                 final int index,
                                 final byte update,
                                 final ByteBinaryAccumulator accumulatorFunction) {
        return atomicAccess.accumulateAndGetByte(
            array,
            checkedByteOffset(array, index),
            update,
            accumulatorFunction
        );
    }
}
