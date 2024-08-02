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

package io.github.ololx.moonshine.bloom.filter;

/**
 * @author Alexander A. Kropotin
 *     project moonshine
 *     created 29/05/2024 4:43pm
 */
public interface CountingBloomFilter extends BloomFilter {

    /**
     * Removes a value from the Bloom filter.
     *
     * @param value the BytesSupplier providing the bytes of the value to be removed.
     *
     * @return true if the value was removed successfully, false otherwise.
     *
     * @apiNote Ensure that the BytesSupplier generates a consistent byte array for the same value
     *     across different instances to maintain the accuracy of the Bloom filter.
     * @implSpec The implementation should ensure that the underlying data structure is updated
     *     to reflect the removing of the existing value.
     */
    boolean remove(BytesSupplier value);
}
