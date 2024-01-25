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

package io.github.ololx.moonshine.bloom.filter;

import io.github.ololx.moonshine.util.concurrent.ConcurrentBitArray;
import io.github.ololx.moonshine.util.concurrent.ConcurrentBitCollection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * ConcurrentBloomFilter is a thread-safe implementation of the BloomFilter interface. It leverages concurrent data
 * structures
 * to ensure that multiple threads can safely add elements to the Bloom filter or check for their absence without
 * causing data corruption.
 * It uses a collection of hash functions to map elements to bit positions in a bit array.
 *
 * @author Alexander A. Kropotin
 * @apiNote This implementation of BloomFilter ensures thread safety in a concurrent environment.
 *     However, the accuracy of the Bloom filter is highly dependent on the quality and independence of the hash
 *     functions provided.
 *     It's crucial to use hash functions that distribute the elements uniformly over the bit array.
 * @project moonshine
 * @created 18/12/2023 10:40 am
 */
public class ConcurrentBloomFilter implements BloomFilter {

    /**
     * The ConcurrentBitCollection that backs the Bloom filter, storing the bits.
     */
    private final ConcurrentBitCollection bits;

    /**
     * The list of HashFunction instances used to hash the elements added to the Bloom filter.
     */
    private final List<HashFunction> hashing;

    /**
     * The size of the bit array.
     */
    private final int size;

    /**
     * Constructs a new ConcurrentBloomFilter with the specified size and hash functions.
     *
     * @param size    the size of the bit array.
     * @param hashing the collection of hash functions to be used.
     *
     * @apiNote The size of the bit array should be chosen carefully based on the expected number of elements
     *     and the desired false positive rate. More hash functions can decrease the false positive rate but increase
     *     the computation time.
     */
    public ConcurrentBloomFilter(int size, Collection<HashFunction> hashing) {
        this.bits = new ConcurrentBitArray(size);
        this.hashing = new ArrayList<>(hashing);
        this.size = size;
    }

    /**
     * Adds a value to the Bloom filter. This operation is thread-safe.
     *
     * @param value the BytesSupplier providing the bytes of the value to be added.
     *
     * @return always true, as the add operation is always successful.
     *
     * @implSpec This method hashes the provided value using all hash functions, then sets the corresponding bits in
     *     the bit array.
     *     The bit positions are determined by aligning the hash codes modulo the size of the bit array.
     */
    @Override
    public boolean add(final BytesSupplier value) {
        for (HashFunction function : this.hashing) {
            int hash = function.apply(value.getBytes());
            int index = align(hash, this.size);

            this.bits.set(index);
        }

        return true;
    }

    /**
     * Checks if a value is absent in the Bloom filter. This operation is thread-safe.
     *
     * @param value the BytesSupplier providing the bytes of the value to be checked.
     *
     * @return true if the value is definitely not in the set, false if the value might be in the set.
     *
     * @implSpec This method hashes the provided value using all hash functions and checks the corresponding bits in
     *     the bit array.
     *     If any of the bits is not set, it returns true indicating the value is definitely not in the set.
     *     Otherwise, it returns false indicating the value might be in the set.
     */
    @Override
    public boolean absent(final BytesSupplier value) {
        for (HashFunction function : this.hashing) {
            int hash = function.apply(value.getBytes());
            int index = align(hash, this.size);

            if (!this.bits.get(index)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Aligns a hash code to fit within the bounds of the bit array size.
     *
     * @param hash the hash code to be aligned.
     * @param size the size of the bit array.
     *
     * @return the index within the bounds of the bit array.
     *
     * @implSpec This method ensures that the index derived from the hash code is within the bounds of the bit array
     *     by taking the absolute value of the hash code modulo the size of the array.
     */
    private static int align(final int hash, final int size) {
        return Math.abs(hash % size);
    }
}