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
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 18/12/2023 10:40 am
 */
public class ConcurrentBloomFilter implements BloomFilter {

    private final ConcurrentBitCollection bits;

    private final HashAlignmentPolicy indexPolicy;

    private final List<HashFunction> hashing;

    private final int size;

    public ConcurrentBloomFilter(int size, Collection<HashFunction> hashing) {
        this.size = size;
        this.bits = new ConcurrentBitArray(size);
        this.hashing = new ArrayList<>(hashing);
        this.indexPolicy = new CircularHashAlignmentPolicy(size);
    }

    @Override
    public boolean add(final BytesSupplier value) {
        for (HashFunction function : hashing) {
            int hash = function.apply(value.getBytes());
            int index = indexPolicy.align(hash);

            this.bits.set(index);
        }

        return true;
    }

    @Override
    public boolean absent(final BytesSupplier value) {
        for (HashFunction function : hashing) {
            int hash = function.apply(value.getBytes());
            int index = indexPolicy.align(hash);

            if (!this.bits.get(index)) {
                return true;
            }
        }

        return false;
    }

    private static final class CircularHashAlignmentPolicy implements HashAlignmentPolicy {

        private final int size;

        private CircularHashAlignmentPolicy(final int size) {
            this.size = size;
        }

        @Override
        public int align(final int hash) {
            return hash % this.size;
        }
    }
}
