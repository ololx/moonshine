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

import java.util.function.Predicate;

/**
 * The BloomFilter interface represents a data structure that is used to perform space-efficient probabilistic checks
 * on whether an element is part of a set. False positive matches are possible, but false negatives are not.
 * In other words, a queried element might be reported as being in the set even if it's not (false positive),
 * but if it is in the set, the Bloom filter will always report it as such.
 *
 * @author Alexander A. Kropotin
 * @apiNote Bloom filters are highly effective if the set elements are known and static, or if there's tolerance
 *     for false positives. It's particularly useful when the cost of a false positive is less significant
 *     than the savings in space. However, it should not be used for security-sensitive applications
 *     where false positives can lead to vulnerabilities.
 *
 *     project moonshine
 *     created 24.11.2023 11:25
 */
public interface BloomFilter extends Predicate<BloomFilter.BytesSupplier> {

    /**
     * Adds a value to the Bloom filter.
     *
     * @param value the BytesSupplier providing the bytes of the value to be added.
     *
     * @return true if the value was added successfully, false otherwise.
     *
     * @apiNote Ensure that the BytesSupplier generates a consistent byte array for the same value
     *     across different instances to maintain the accuracy of the Bloom filter.
     * @implSpec The implementation should ensure that the underlying data structure is updated
     *     to reflect the addition of the new value.
     */
    boolean add(BytesSupplier value);

    /**
     * Checks if a value is absent in the Bloom filter.
     *
     * @param value the BytesSupplier providing the bytes of the value to be checked.
     *
     * @return true if the value is definitely not in the set, false if the value might be in the set.
     *
     * @apiNote This method may return false positives (indicating a value is in the set when it's not)
     *     but will never return false negatives (indicating a value is not in the set when it is).
     */
    boolean absent(BytesSupplier value);

    /**
     * Checks if a value is present in the Bloom filter.
     *
     * @param value the BytesSupplier providing the bytes of the value to be checked.
     *
     * @return true if the value is may be in the set, false if the value definitely not in the set.
     *
     * @apiNote This method may return false positives (indicating a value is in the set when it's not)
     *     but will never return false negatives (indicating a value is not in the set when it is).
     */
    @Override
    default boolean test(BytesSupplier value) {
        return !absent(value);
    }

    /**
     * Returns the size of the Bloom filter. The size is the total number of bits in the underlying bit array
     * and is indicative of the space complexity of the filter. It's directly related to the capacity of the
     * Bloom filter and its ability to minimize false positives.
     *
     * @return the size of the Bloom filter as an integer.
     *
     * @apiNote The size of the Bloom filter is a crucial factor in its effectiveness. A larger size reduces the
     *     probability of false positives but increases the space usage. It's essential to balance the size
     *     with the expected number of elements and the acceptable false positive rate based on the use case.
     */
    int size();

    /**
     * The BytesSupplier interface provides a method to supply bytes.
     * It's designed to abstract the way byte arrays are obtained.
     */
    interface BytesSupplier {

        /**
         * Returns a byte array.
         *
         * @return the byte array provided by this BytesSupplier.
         *
         * @apiNote The byte array returned should uniquely represent the value intended to be checked or added
         *     to the Bloom filter. Consistency in the output for the same value is crucial.
         */
        byte[] getBytes();
    }

    /**
     * The HashFunction interface represents a function that computes a hash code for a given byte array.
     * It's designed to be used in the context of a Bloom filter to hash values being added or checked.
     */
    interface HashFunction {

        /**
         * Applies the hash function to the given byte array.
         *
         * @param value the byte array to be hashed.
         *
         * @return the resulting hash code.
         *
         * @apiNote The hash function should distribute the values uniformly to minimize collisions.
         *     A non-uniform distribution increases the probability of false positives.
         */
        int apply(byte[] value);
    }

    /**
     * The HashingStrategy interface represents a strategy for applying a hash function to a value.
     * This strategy is aimed at determining the appropriate bit index in the Bloom filter.
     * For example, if the hash function returns an index greater than the number of elements in the filter,
     * it can calculate the appropriate index by wrapping around or using the last possible index.
     */
    interface HashingStrategy {

        /**
         * Applies the hashing strategy to the given byte array using the specified hash function.
         * This method determines the appropriate index in the Bloom filter bit array.
         *
         * @param value        the byte array to be hashed.
         * @param hashFunction the hash function to be used for hashing the value.
         *
         * @return the appropriate bit index in the Bloom filter.
         *
         * @apiNote The hashing strategy should ensure that the calculated index is valid for the Bloom filter's
         *     size. Strategies may include wrapping the index around if it exceeds the filter size or using
         *     a modulo operation to fit the index within the valid range.
         */
        int apply(byte[] value, HashFunction hashFunction);
    }
}
