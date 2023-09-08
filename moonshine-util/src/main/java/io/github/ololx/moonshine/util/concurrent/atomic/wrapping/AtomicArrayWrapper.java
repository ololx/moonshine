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

package io.github.ololx.moonshine.util.concurrent.atomic.wrapping;

/**
 * A generic interface for working with atomic arrays.
 *
 * @apiNote This interface provides atomic operations on arrays, allowing
 *     for concurrent modifications while maintaining consistency.
 *     Implementations of this interface may use various strategies
 *     to achieve thread-safety, such as using low-level hardware
 *     instructions or synchronization mechanisms.
 * @implNote Implementations of this interface are expected to ensure atomicity
 *     and thread-safety for the provided methods. The specific
 *     implementation details may vary, and the performance characteristics
 *     could be influenced by the underlying hardware, JVM, and concurrency
 *     mechanisms.
 *
 * @param <T> the type of elements stored in the array
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 25.08.2023 16:07
 */
public interface AtomicArrayWrapper<T> {

    /**
     * Returns the length of the array.
     *
     * @return the length of the array
     */
    int length();

    /**
     * Gets the current value at the specified index.
     *
     * @param i the index
     *
     * @return the current value at the specified index
     */
    T get(int i);

    /**
     * Sets the element at the specified index to the given value.
     *
     * @param i        the index
     * @param newValue the new value to set
     */
    void set(int i, T newValue);

    /**
     * Atomically sets the element at the specified index to the given value
     * and returns the previous value.
     *
     * @param i        the index
     * @param newValue the new value to set
     *
     * @return the previous value at the specified index
     */
    T getAndSet(int i, T newValue);

    /**
     * Atomically sets the element at the specified index to the given update
     * value if the current value at that index is equal to the expected value.
     *
     * @param i      the index
     * @param expect the expected value
     * @param update the new value
     *
     * @return {@code true} if the value was updated, {@code false} otherwise
     */
    boolean compareAndSet(int i, T expect, T update);

    /**
     * Atomically increments the element at the specified index by one and
     * returns the previous value.
     *
     * @param i the index
     *
     * @return the previous value at the specified index
     */
    T getAndIncrement(int i);

    /**
     * Atomically decrements the element at the specified index by one and
     * returns the previous value.
     *
     * @param i the index
     *
     * @return the previous value at the specified index
     */
    T getAndDecrement(int i);

    /**
     * Atomically adds the given delta to the element at the specified index
     * and returns the previous value.
     *
     * @param i     the index
     * @param delta the value to add
     *
     * @return the previous value at the specified index
     */
    T getAndAdd(int i, T delta);

    /**
     * Atomically increments the element at the specified index by one and
     * returns the updated value.
     *
     * @param i the index
     *
     * @return the updated value at the specified index
     */
    T incrementAndGet(int i);

    /**
     * Atomically decrements the element at the specified index by one and
     * returns the updated value.
     *
     * @param i the index
     *
     * @return the updated value at the specified index
     */
    T decrementAndGet(int i);

    /**
     * Atomically adds the given delta to the element at the specified index
     * and returns the updated value.
     *
     * @param i     the index
     * @param delta the value to add
     *
     * @return the updated value at the specified index
     */
    T addAndGet(int i, T delta);
}
