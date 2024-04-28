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

import io.github.ololx.moonshine.bloom.filter.strategies.CyclicStrategy;
import io.github.ololx.moonshine.bloom.filter.strategies.PowerOfTwoStrategy;
import io.github.ololx.moonshine.util.concurrent.ConcurrentBitArray;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
 *
 *     project moonshine
 *     created 18.12.2023 10:40
 */
public class BasicBloomFilter implements BloomFilter {

    /**
     * The list of HashFunction instances used to hash the elements added to the Bloom filter.
     */
    private final List<HashFunction> hashing;

    private final BitStrategy bitStrategy;

    /**
     * Constructs a new ConcurrentBloomFilter with the specified size and hash functions.
     *
     * @param bitStrategy    the size of the bit array.
     * @param hashing the collection of hash functions to be used.
     *
     * @apiNote The size of the bit array should be chosen carefully based on the expected number of elements
     *     and the desired false positive rate. More hash functions can decrease the false positive rate but increase
     *     the computation time.
     */
    public BasicBloomFilter(final BitStrategy bitStrategy, final Collection<HashFunction> hashing) {
        this.bitStrategy = bitStrategy;
        this.hashing = new ArrayList<>(hashing);
    }

    public static BasicBloomFilter newInstance(int size, Collection<HashFunction> hashing, int strategy) {
        switch (strategy) {
            case 0:
                return new BasicBloomFilter(new CyclicStrategy(new ConcurrentBitArray(size)), hashing);
            case 1:
                return new BasicBloomFilter(new PowerOfTwoStrategy(new ConcurrentBitArray(PowerOfTwoStrategy.nextPowerOfTwo(size))), hashing);
            default:
                throw new IllegalArgumentException("The strategy must be define in interval of [0, 1]");
        }
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
            this.bitStrategy.set(hash);
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

            if (!this.bitStrategy.get(hash)) {
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
        return this.bitStrategy.getBits().size();
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
        return this.bitStrategy.getBits().cardinality();
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
}
