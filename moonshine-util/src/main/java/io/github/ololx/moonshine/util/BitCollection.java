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

package io.github.ololx.moonshine.util;

/**
 * An interface for a concurrent bit collection, providing methods to manipulate
 * individual bits.
 *
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 14.08.2023 13:51
 */
public interface BitCollection {

    /**
     * Gets the value of the bit at the specified index.
     *
     * @param bitIndex The index of the bit to retrieve.
     *
     * @return The value of the bit (true or false) at the specified index.
     */
    boolean get(int bitIndex);

    /**
     * Sets the bit at the specified index to 1.
     *
     * @param bitIndex The index of the bit to set.
     */
    void set(int bitIndex);

    /**
     * Clears the bit at the specified index (sets it to 0).
     *
     * @param bitIndex The index of the bit to clear.
     */
    void clear(int bitIndex);

    /**
     * Flips the value of the bit at the specified index (0 becomes 1, and 1 becomes 0).
     *
     * @param bitIndex The index of the bit to flip.
     */
    void flip(int bitIndex);

    /**
     * Returns the number of bits in the collection that are set to 1. This is often referred to as the
     * <a href="https://en.wikipedia.org/wiki/Hamming_weight">Hamming weight</a> or pop count.
     *
     * @return The number of bits set to 1.
     */
    int cardinality();

    int size();
}
