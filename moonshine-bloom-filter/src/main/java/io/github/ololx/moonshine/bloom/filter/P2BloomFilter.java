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

/**
 * P2BloomFilter is a thread-safe implementation of the BloomFilter interface. It leverages concurrent data
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
 *
 *     project moonshine
 *     created 18.12.2023 10:40
 */
public class P2BloomFilter implements BloomFilter {

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
     * Constructs a new {@code P2BloomFilter} with the specified size for the internal bit array and a collection
     * of hash functions for element hashing. The actual size of the bit array is adjusted to be a power of two
     * to optimize the bloom filter's performance.
     *
     * @param size    the initial size of the bit array. This will be adjusted to the next power of two.
     * @param hashing a collection of {@code HashFunction} objects to be used for hashing elements.
     *                Each function should distribute the elements uniformly over the bit array.
     *
     * @apiNote The size of the bit array should be chosen carefully based on the expected number of elements
     *     to be stored in the bloom filter and the desired false positive rate. Using more hash functions
     *     can reduce the false positive rate but will also increase the time to compute the hashes
     *     and check for element presence. It is recommended to balance the number of hash functions
     *     and the size of the bit array to achieve the desired performance and accuracy.
     */
    public P2BloomFilter(int size, Collection<HashFunction> hashing) {
        this.size = nextPowerOfTwo(size);
        this.bits = new ConcurrentBitArray(this.size);
        this.hashing = new ArrayList<>(hashing);
    }

    /**
     * Calculates and returns the smallest power of two that is greater than or equal to the given integer.
     * If the given integer is already a power of two, it returns the same value. This method ensures that
     * the size of the bit array in the bloom filter is optimized for bitwise operations.
     *
     * @param x the integer for which to find the next power of two.
     *
     * @return the next power of two greater than or equal to {@code x}. If {@code x} is less than 1,
     *     the return value is 1, ensuring that the bit array has a positive size.
     *
     * @implNote This method operates by first decrementing {@code x} by 1 to handle the case where
     *     {@code x} is already a power of two. Then, it progressively sets all lower bits to 1
     *     by bitwise OR operations with right-shifted versions of {@code x}. Finally, it increments
     *     {@code x} by 1 to reach the next power of two. This approach ensures that the computation
     *     is efficient and only requires a logarithmic number of steps in the size of the integer.
     */
    private static int nextPowerOfTwo(int x) {
        if (x < 1) {
            return 1;
        }

        if ((x & (x - 1)) == 0) {
            return x;
        }

        x--;
        x |= x >> 1;
        x |= x >> 2;
        x |= x >> 4;
        x |= x >> 8;
        x |= x >> 16;
        x++;

        return x;
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
     * Returns the size of the Bloom filter. The size represents the number of bits in the underlying
     * ConcurrentBitCollection and is an important factor that influences the probability of false positives.
     * The size is indicative of the space complexity and capacity of the Bloom filter.
     *
     * @return the size of the Bloom filter as an integer, representing the number of bits in the underlying bit array.
     *
     * @apiNote The size of the Bloom filter determines its capacity to store elements and directly influences the
     *     false
     *     positive rate. A larger size typically means a lower false positive rate, but it also means higher space
     *     consumption. Choosing the right size is crucial and should be based on the expected number of elements and
     *     the acceptable false positive rate for your specific use case. In a concurrent environment, it's also
     *     important
     *     to ensure that the size is sufficient to minimize collisions and maintain performance.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Returns the approximate number of distinct elements that have been added to the Bloom filter.
     * This count is derived from the underlying ConcurrentBitCollection, which may provide a more accurate
     * count in a concurrent environment.
     *
     * @return the approximate count of distinct elements in the Bloom filter.
     *
     * @apiNote The cardinality estimation in the concurrent context may be subject to the behavior of the
     *     underlying
     *     concurrent data structure used for bit storage. It aims to provide a thread-safe estimate of the number of
     *     elements added to the Bloom filter, but like all Bloom filters, it may not be perfectly accurate due to the
     *     probabilistic nature of the data structure.
     */
    @Override
    public int cardinality() {
        return this.bits.cardinality();
    }

    /**
     * Checks whether the Bloom filter is empty, meaning no elements have been added to it.
     * This check is based on the cardinality of the underlying ConcurrentBitCollection.
     *
     * @return true if the Bloom filter is empty (cardinality is zero), false otherwise.
     *
     * @apiNote In a concurrent environment, the isEmpty check is thread-safe and reflects the state of the
     *     Bloom filter at the moment of the call. However, due to the nature of concurrent operations, the state
     *     can change rapidly, so the result should be used with an understanding that it may not represent
     *     the state at a later time. An empty Bloom filter means that no bits are set in the bit array, but
     *     like all Bloom filters, it does not guarantee that no elements have been added, especially in a
     *     highly concurrent scenario.
     */
    @Override
    public boolean isEmpty() {
        return this.cardinality() == 0;
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
        return (hash & (size - 1));
    }
}
