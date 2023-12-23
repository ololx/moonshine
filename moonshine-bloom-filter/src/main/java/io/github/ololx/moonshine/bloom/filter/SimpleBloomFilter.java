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

import java.util.BitSet;
import java.util.List;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 18/12/2023 10:40 am
 */
public class SimpleBloomFilter implements BloomFilter {

    private final BitSet bits;

    private final List<HashFunction> hashes;

    public SimpleBloomFilter(int size, List<HashFunction> hashes) {
        this.bits = new BitSet(size);
        this.hashes = hashes;
    }

    @Override
    public boolean add(final BytesSupplier value) {
        this.hashes.forEach(vHashFunction -> {
            int index = vHashFunction.apply(value.getBytes());
            index = index % this.bits.size();
            this.bits.set(index);
        });

        return true;
    }

    @Override
    public boolean contains(final BytesSupplier value) {
        for (HashFunction vHashFunction : hashes) {
            int index = vHashFunction.apply(value.getBytes()) % this.bits.size();

            if (!this.bits.get(index)) {
                return false;
            };
        }

        return true;
    }
}
