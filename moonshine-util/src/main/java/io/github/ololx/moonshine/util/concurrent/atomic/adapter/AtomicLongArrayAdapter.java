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

package io.github.ololx.moonshine.util.concurrent.atomic.adapter;

import java.util.concurrent.atomic.AtomicLongArray;

/**
 * A wrapper class that implements the {@link AtomicArrayAdapter} interface using
 * {@link java.util.concurrent.atomic.AtomicLongArray} as the underlying data structure.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 27.08.2023 16:08
 */
public class AtomicLongArrayAdapter implements AtomicArrayAdapter<Long> {

    private final AtomicLongArray array;

    /**
     * Constructs a new {@code AtomicLongArrayWrapper} instance with the specified length.
     *
     * @param length The length of the array.
     */
    public AtomicLongArrayAdapter(int length) {
        array = new AtomicLongArray(length);
    }

    /**
     * Constructs a new {@code AtomicLongArrayWrapper} instance with the same length as, and all elements copied
     * from, the given array.
     *
     * @param array the array to copy elements from
     *
     * @throws NullPointerException if array is null
     */
    public AtomicLongArrayAdapter(long[] array) {
        this.array = new AtomicLongArray(array);
    }

    /**
     * Returns the length of the array.
     *
     * @return The length of the array.
     */
    @Override
    public int length() {
        return array.length();
    }

    /**
     * Retrieves the element at the specified index in the array.
     *
     * @param i The index of the element to retrieve.
     *
     * @return The element at the specified index.
     */
    @Override
    public Long get(int i) {
        return array.get(i);
    }

    /**
     * Sets the element at the specified index to the given value.
     *
     * @param i        The index of the element to set.
     * @param newValue The new value to set.
     */
    @Override
    public void set(int i, Long newValue) {
        array.set(i, newValue);
    }

    /**
     * Atomically sets the element at the specified index to the given value and returns the previous value.
     *
     * @param i        The index of the element to set.
     * @param newValue The new value to set.
     *
     * @return The previous value at the specified index.
     */
    @Override
    public Long getAndSet(int i, Long newValue) {
        return array.getAndSet(i, newValue);
    }

    /**
     * Atomically sets the element at the specified index to the given value if the current value is equal to the
     * expected value.
     *
     * @param i      The index of the element to set.
     * @param expect The expected value.
     * @param update The new value to set if the current value is equal to the expected value.
     *
     * @return {@code true} if successful; {@code false} otherwise.
     */
    @Override
    public boolean compareAndSet(int i, Long expect, Long update) {
        return array.compareAndSet(i, expect, update);
    }

    /**
     * Atomically increments the element at the specified index by one and returns the previous value.
     *
     * @param i The index of the element to increment.
     *
     * @return The previous value at the specified index.
     */
    @Override
    public Long getAndIncrement(int i) {
        return array.getAndIncrement(i);
    }

    /**
     * Atomically decrements the element at the specified index by one and returns the previous value.
     *
     * @param i The index of the element to decrement.
     *
     * @return The previous value at the specified index.
     */
    @Override
    public Long getAndDecrement(int i) {
        return array.getAndDecrement(i);
    }

    /**
     * Atomically adds the given value to the element at the specified index and returns the previous value.
     *
     * @param i     The index of the element to which the value will be added.
     * @param delta The value to add.
     *
     * @return The previous value at the specified index.
     */
    @Override
    public Long getAndAdd(int i, Long delta) {
        return array.getAndAdd(i, delta);
    }

    /**
     * Atomically increments the element at the specified index by one and returns the updated value.
     *
     * @param i The index of the element to increment.
     *
     * @return The updated value at the specified index.
     */
    @Override
    public Long incrementAndGet(int i) {
        return array.incrementAndGet(i);
    }

    /**
     * Atomically decrements the element at the specified index by one and returns the updated value.
     *
     * @param i The index of the element to decrement.
     *
     * @return The updated value at the specified index.
     */
    @Override
    public Long decrementAndGet(int i) {
        return array.decrementAndGet(i);
    }

    /**
     * Atomically adds the given value to the element at the specified index and returns the updated value.
     *
     * @param i     The index of the element to which the value will be added.
     * @param delta The value to add.
     *
     * @return The updated value at the specified index.
     */
    @Override
    public Long addAndGet(int i, Long delta) {
        return array.addAndGet(i, delta);
    }

    /**
     * Returns the string representation of the underlying {@link java.util.concurrent.atomic.AtomicLongArray}.
     *
     * @return The string representation of the array.
     */
    @Override
    public String toString() {
        return array.toString();
    }
}
