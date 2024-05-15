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

package io.github.ololx.moonshine.bloom.filter.strategies;

import io.github.ololx.moonshine.bloom.filter.BloomFilter;
import io.github.ololx.moonshine.util.BitCollection;

import java.util.Objects;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 28/04/2024 8:15pm
 */
public class CyclicStrategy implements BloomFilter.FilterState {

    /**
     * The BitCollection that backs the Bloom filter, storing the bits.
     */
    private final BitCollection bits;

    public CyclicStrategy(final BitCollection bits) {
        this.bits = Objects.requireNonNull(bits, "The bit collection must be not null");
    }

    @Override
    public void set(final int index) {
        this.bits.set(align(index, this.bits.size()));
    }

    @Override
    public boolean get(final int index) {
        return this.bits.get(align(index, this.bits.size()));
    }

    @Override
    public BitCollection getBits() {
        return this.bits;
    }

    /**
     * Aligns an index code to fit within the bounds of the bit array size.
     *
     * @param index the index code to be aligned.
     * @param size the size of the bit array.
     *
     * @return the index within the bounds of the bit array.
     *
     * @implSpec This method ensures that the index derived from the index code is within the bounds of the bit array
     *     by taking the absolute value of the index code modulo the size of the array.
     */
    private int align(final int index, final int size) {
        return Math.abs(index % size);
    }
}
